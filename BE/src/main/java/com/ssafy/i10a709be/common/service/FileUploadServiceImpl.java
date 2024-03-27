package com.ssafy.i10a709be.common.service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.i10a709be.common.entity.Files;
import com.ssafy.i10a709be.common.exception.*;
import com.ssafy.i10a709be.common.repository.FileRepository;
import com.ssafy.i10a709be.common.util.ImageUtils;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String IMAGE_CONTENT_TYPE_PREFIX = "image";
    private final AmazonS3Client amazonS3Client;
    private final FileRepository fileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public Files uploadFile(MultipartFile multipartFile) {

        if (isEmpty(multipartFile)) {
            throw new MultiPartFileNotFoundException("제공된 파일이 없습니다",this);
        }


        return uploadFiles(multipartFile, false);
    }


    private boolean isEmpty(MultipartFile multipartFile) {
        return multipartFile == null || multipartFile.isEmpty();
    }

    private Files uploadFiles(MultipartFile multipartFile, boolean resize) {
        File uploadFile =
                convert(multipartFile, resize) // 파일 변환할 수 없으면 에러
                        .orElseThrow(() -> new InternalServerException("MultipartFile -> File 변환 실패", this));

        return upload(uploadFile, multipartFile.getContentType());
    }

    private Files saveFiles(File uploadFile, String fileName, String ext) {
        String originFileName = uploadFile.getName();
        Files fileEntity = Files.builder()
                .name( originFileName )
                .source( fileName )
                .fileType( ext )
                .build();
        try{
            Files saved = fileRepository.save( fileEntity );
            log.info(String.valueOf(saved.getFileId()) +"번 파일 저장 성공");
            return saved;
        }catch( PersistenceException e ){
            log.error( "파일 저장 간 에러 발생 : " + e.getMessage() );
            throw new InternalServerException( "파일 저장 간 에러가 발생했습니다.", this );
        }


    }

    private Files upload(File uploadFile, String contentType) {
        String fileName = "upload/" + UUID.randomUUID(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        //파일 DB에 정보 저장
        Files saved = saveFiles( uploadFile, uploadImageUrl, contentType );
        removeNewFile(uploadFile);

        return saved;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.debug("Local File Delete Failed!! ");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file, boolean resize) {
        String path = System.getProperty("user.dir") + "\\" + file.getOriginalFilename();

        try {
            File convertFile = makeFile(path, file.getBytes());

            if (resize) {
                BufferedImage resizedImage = ImageUtils.resizeImage(convertFile, 320, 320);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(
                        resizedImage, getExtension(file.getOriginalFilename()), byteArrayOutputStream);
                convertFile.delete();

                File resizedFile = makeFile(path, byteArrayOutputStream.toByteArray());
                return Optional.of(resizedFile);
            }
            return Optional.of(convertFile);
        } catch (InterruptedException | IOException e) {
            throw new InternalServerCaughtException(e, this);
        }
    }

    private File makeFile(String path, byte[] bytes) throws IOException {
        File file = new File(path);

        if (file.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos =
                         new FileOutputStream(file)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(bytes);
            }
        }
        return file;
    }

    private String getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElseThrow(() -> new InternalServerException("파일 이름이 비었습니다", this));
    }
}

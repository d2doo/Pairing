import classNames from "classnames";
import React, { ReactNode, useEffect, useState, useRef } from "react";
import { useLocalAxios } from "@/utils/axios.ts";
import { AxiosError, AxiosProgressEvent, AxiosResponse } from "axios";
import { Progress } from "@/components/ui/progress.tsx";

interface ChatImageUploaderProps {
  children?: ReactNode;
  className?: string;
  onUploadRequested?: (preview: string) => void;
  onUploadComplete?: (response: AxiosResponse) => void;
  onError?: (error: Error | unknown) => void;
  onRemove?: () => void;
  clear?: boolean;
  // disabled?: boolean;
  open?: boolean;
}

enum ImageUploadStatus {
  ERROR,
  PENDING,
  UPLOADING,
  COMPLETE,
}

export const ChatImageUploader = (props: ChatImageUploaderProps) => {
  const localAxios = useLocalAxios();
  const [selectedFile, setSelectedFile] = useState<File>();
  const [preview, setPreview] = useState<string>();
  const [uploadStatus, setUploadStatus] = useState<ImageUploadStatus>(
    ImageUploadStatus.PENDING,
  );
  const [progress, setProgress] = useState<number>(0);
  const inputRef = useRef<HTMLInputElement>(null);
  const handleOpenFileDialog = () => {
    // 숨겨진 input 요소를 프로그래매틱하게 클릭하여 파일 선택 창을 연다
    inputRef.current?.click();
  };

  useEffect(() => {
    if (selectedFile) {
      const imageURL = URL.createObjectURL(selectedFile);
      setPreview(imageURL);

      if (props.onUploadRequested) props.onUploadRequested(imageURL);
      return () => {
        // Unmount시 생성했던 URL 해제 (Memory Leak)
        URL.revokeObjectURL(imageURL);
      };
    }
  }, [selectedFile]);

  useEffect(() => {
    if (props.open) {
      inputRef.current?.click();
    }
  }, [props.open]);

  useEffect(() => {
    if (props.clear) {
      setPreview(undefined);
      setSelectedFile(undefined);
      setUploadStatus(ImageUploadStatus.PENDING);
      setProgress(0);
    }
  }, [props.clear]);

  const onChangeImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    if (e.target.files) {
      setUploadStatus(ImageUploadStatus.UPLOADING);

      const targetImage = e.target.files[0];
      setSelectedFile(targetImage);

      const formData = new FormData();
      formData.append("image", targetImage);

      try {
        const response = await localAxios.post("/api/common/image", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          onUploadProgress: (data: AxiosProgressEvent) => {
            if (data.total)
              setProgress(Math.round((100 * data.loaded) / data.total));
          },
        });

        setUploadStatus(ImageUploadStatus.COMPLETE);
        if (props.onUploadComplete) props.onUploadComplete(response);
      } catch (e) {
        console.error(e);
        setUploadStatus(ImageUploadStatus.ERROR);

        if (props.onError) props.onError(e);
        return;
      }
    }
  };

  return (
    <label
      className={classNames(
        "border-gray relative flex h-14 w-14 flex-col items-center justify-center rounded-lg border-2",
        props.className,
      )}
    >
      {(uploadStatus == ImageUploadStatus.COMPLETE ||
        uploadStatus == ImageUploadStatus.ERROR) && (
        <>
          <button
            className="material-symbols-outlined absolute right-0 top-0 rounded-md bg-blue1 px-1 py-0.5 text-xs font-bold text-white"
            onClick={props.onRemove}
          >
            close
          </button>
          <button onClick={handleOpenFileDialog} className="hidden"></button>
        </>
      )}
      {selectedFile && (
        <img src={preview} className="h-full w-full object-scale-down" />
      )}
      {!selectedFile && (
        <>
          <span className="material-symbols-outlined">photo_camera</span>
          <span className="text-xs">{props.children}</span>
        </>
      )}
      {uploadStatus == ImageUploadStatus.UPLOADING && (
        <Progress className="absolute bottom-1/4 h-2 w-3/4" value={progress} />
      )}
      {uploadStatus == ImageUploadStatus.PENDING && (
        <input
          type="file"
          accept="image/*"
          hidden
          onChange={onChangeImage}
          ref={inputRef}
        />
      )}
    </label>
  );
};

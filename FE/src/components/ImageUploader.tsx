import classNames from 'classnames';
import React, {ReactNode, useEffect, useState} from "react";
import { useLocalAxios } from "@/utils/axios.ts";
import { AxiosProgressEvent, AxiosResponse } from "axios";
import { Progress } from "@/components/ui/progress.tsx";

interface ImageUploaderProps {
  children?: ReactNode;
  className?: string;
  disabled?: boolean;
  onUploadRequested?: () => void;
  onUploadComplete?: (response: AxiosResponse) => void;
  onError?: (error: Error | unknown) => void;
  onRemove?: () => void;
}

enum ImageUploadStatus {
    ERROR,
    PENDING,
    UPLOADING,
    COMPLETE
}

export const ImageUploader = (props: ImageUploaderProps) => {
    const localAxios = useLocalAxios();
    const [ selectedFile, setSelectedFile ] = useState<File>();
    const [ preview, setPreview ] = useState<string>();
    const [ uploadStatus, setUploadStatus ] = useState<ImageUploadStatus>(ImageUploadStatus.PENDING);
    const [ progress, setProgress ] = useState<number>( 0);

    useEffect(() => {
        if (selectedFile) {
            const imageURL = URL.createObjectURL(selectedFile);
            setPreview(imageURL);

            return () => {
                // Unmount시 생성했던 URL 해제 (Memory Leak)
                URL.revokeObjectURL(imageURL);
            }
        }

    }, [selectedFile]);

    const onChangeImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();

        if (e.target.files) {
            setUploadStatus(ImageUploadStatus.UPLOADING);
            if (props.onUploadRequested) props.onUploadRequested();

            const targetImage = e.target.files[0];
            setSelectedFile(targetImage);

            const formData = new FormData();
            formData.append('image', targetImage);

      try {
        const response = await localAxios.post("/common/image", formData, {
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
            }
            catch (e) {
                console.error(e);
                setUploadStatus(ImageUploadStatus.ERROR);

                if (props.onError) props.onError(e);
                return;
            }


        }
    }

  return (
    <label
      className={classNames(
        "border-gray relative flex h-14 w-14 flex-col items-center justify-center rounded-lg border-2 p-1",
        props.className,
      )}
    >
      {(uploadStatus == ImageUploadStatus.COMPLETE ||
        uploadStatus == ImageUploadStatus.ERROR) && (
        <button
          className="material-symbols-outlined absolute right-0 top-0 rounded-md bg-red-600 px-1 py-0.5 text-xs font-bold text-white"
          onClick={props.onRemove}
        >
          close
        </button>
      )}
      {selectedFile && <img src={preview} className="h-full w-full" />}
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
          disabled={props.disabled}
        />
      )}
    </label>
  );
};

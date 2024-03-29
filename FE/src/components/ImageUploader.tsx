import classNames from 'classnames';
import React, {ReactNode, useEffect, useState} from "react";
import { useLocalAxios } from "@/utils/axios.ts";
import {AxiosError, AxiosProgressEvent, AxiosResponse} from "axios";
import {Progress} from "@/components/ui/progress.tsx";

interface ImageUploaderProps {
    children?: ReactNode;
    className?: string;
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
                const response = await localAxios.post("/api/common/image", formData, {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    },
                    onUploadProgress: (data: AxiosProgressEvent) => {
                        if (data.total) setProgress(Math.round((100 * data.loaded) / data.total))
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
        <label className={classNames('w-14 h-14 border-2 border-gray rounded-lg flex flex-col justify-center items-center p-1 relative', props.className)}>
            { (uploadStatus == ImageUploadStatus.COMPLETE || uploadStatus == ImageUploadStatus.ERROR) &&
                <button className='material-symbols-outlined absolute right-0 top-0 rounded-md bg-red-600 text-white text-xs font-bold px-1 py-0.5'
                        onClick={props.onRemove}
                >close</button>
            }
            { selectedFile && <img src={preview} className='w-full h-full' /> }
            {
                !selectedFile && <>
                    <span className="material-symbols-outlined">photo_camera</span>
                    <span className="text-xs">{props.children}</span>
                </>
            }
            {
                uploadStatus == ImageUploadStatus.UPLOADING &&
                    <Progress className='absolute h-2 w-3/4 bottom-1/4' value={progress} />
            }
            { uploadStatus == ImageUploadStatus.PENDING && <input type="file" accept="image/*" hidden onChange={onChangeImage} /> }
        </label>
    );
};

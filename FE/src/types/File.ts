interface ImageRequest{
    imageIds: number[]
}

interface ImageResponse{
    imgUrl:string;
    fileId:number;
}

export type{ ImageRequest,ImageResponse }
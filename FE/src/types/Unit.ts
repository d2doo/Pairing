import { PartType } from "./PartType";

interface UnitSaveRequest{
    categoryId: number,
    isCombinable: boolean,
    unitDescription: string,
    price: number,
    age: number,
    images: number[],
    status: string,
    partTypeIds: number[],
}
interface UnitFind{
    memberId: string,
    nickname: string,
    score: number,
    unitImages: string[],
    positions: PartType,
    age: number,
}



export type { UnitSaveRequest, UnitFind }
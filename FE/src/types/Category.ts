import { PartType } from "./PartType";

interface Category{
    categoryId: number,
    mainCategory: string,
    subCategory: string,
    partTypes: PartType[]
}

export type { Category };
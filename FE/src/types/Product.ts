import {MemberSummaryResponse} from "@/types/Member.ts";
import { UnitFind, UnitSaveRequest } from "./Unit";

interface PartType {
    partTypeId: number;
    position: string;
}

interface Category {
    categoryId: number;
    mainCategory: string;
    subCategory: string;
    partType: PartType
}

interface UnitResponse {
    age: number;
    memberId: string;
    nickname: string;
    positions: PartType[];
    score: number;
    unitDescription: string;
    unitImages: string[];
}

interface ProductDetailResponse {
    category: Category;
    leader: MemberSummaryResponse;
    maxAge: number;
    productId: number;
    productTitle: string;
    thumbnailUrl: string;
    totalPrice: number;
    units: UnitResponse[];
}

interface ProductSaveRequest{
    productTitle: string,
    unit: UnitSaveRequest,
    targetUnits: number[],
    thumbnailIndex: number,
}

interface ProductFindResponse{
    thumbnailUrl: string,
    category: Category,
    leader: MemberSummaryResponse,
    productId: string,
    productTitle: string,
    totalPrice: number,
    maxAge: number,
    units: UnitFind[],
}

export type { PartType, Category, UnitResponse, ProductDetailResponse, ProductSaveRequest, ProductFindResponse };
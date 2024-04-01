import { MemberSummaryResponse } from "@/types/Member.ts";
import { Category } from "./Category";
import { UnitFind, UnitSaveRequest } from "./Unit";

interface PartType {
  partTypeId: number;
  position: string;
}

interface UnitResponse {
  age: number;
  memberId: string;
  nickname: string;
  positions: PartType[];
  score: number;
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

interface ProductSaveRequest {
  productTitle: string;
  unit: UnitSaveRequest;
  targetUnits: number[];
  thumbnailIndex: number;
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

interface ProudctPreview {
  thumbnailUrl: string;
  category: string[];
  productId: string;
  productTitle: string;
  totalPrice: number;
}

export type {
  PartType,
  Category,
  UnitResponse,
  ProductDetailResponse,
  ProductSaveRequest,
  ProductFindResponse,
  ProudctPreview,
};

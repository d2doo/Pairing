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
  unitDescription: string;
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

interface ProductFindResponse {
  thumbnailUrl: string;
  category: Category;
  leader: MemberSummaryResponse;
  productId: string;
  productTitle: string;
  totalPrice: number;
  maxAge: number;
  units: UnitFind[];
}

interface ProudctPreview {
  thumbnailUrl: string;
  category: string[];
  productId: string;
  productTitle: string;
  totalPrice: number;
}

interface ProductRequestParams {
  isOnly?: boolean; // true이면 개별, false이면 조합 상품
  isCombined?: boolean;
  memberId?: string; // mypage 내 상품 조회시 필요
  nickname?: string; //판매 유저(LEAD)
  categoryId?: number; //에어팟, 버즈
  productStatus?: string; // 판매중 혹은 판매 완료
  startPrice?: number;
  endPrice?: number;
  maxAge?: number;
  keyword?: string;
  page?: number;
  size: number;
  productId?: number;
}

export type {
  PartType,
  Category,
  UnitResponse,
  ProductDetailResponse,
  ProductSaveRequest,
  ProductFindResponse,
  ProudctPreview,
  ProductRequestParams,
};

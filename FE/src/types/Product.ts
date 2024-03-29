import { Category } from "./Category";
import { MemberSummaryResponse } from "./Member";
import { UnitFind, UnitSaveRequest } from "./Unit";

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


export type { ProductSaveRequest, ProductFindResponse };
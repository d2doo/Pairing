import { PartType } from "./PartType";

interface UnitSaveRequest {
  categoryId: number;
  isCombinable: boolean;
  unitDescription: string;
  price: number;
  age: number;
  images: number[];
  status: string;
  partTypeIds: number[];
}
interface UnitFind {
  memberId: string;
  nickname: string;
  score: number;
  unitImages: string[];
  positions: PartType;
  age: number;
}

interface UnitFindTwo {
  memberId: string;
  nickname: string;
  score: number;
  unitImages: string[];
  positions: PartType[];
  age: number;
}

interface UnitResponse {
  unitId: number;
  productId: number;
  categoryId: number;
  isCombinable: boolean;
  unitDescription: string;
  price: number;
  age: number;
  images: string[];
  status: string;
}

export type { UnitResponse, UnitSaveRequest, UnitFind, UnitFindTwo };

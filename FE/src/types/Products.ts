interface ProudctPreview {
  thumbnailUrl: string;
  category: string[];
  productId: string;
  productTitle: string;
  totalPrice: number;
}

interface ProductListResponse {
  productId: number;
  leader: Leader;
  productTitle: string;
  thumbnailUrl: string;
  category: Category;
  units: Units[];
  totalPrice: number;
  maxAge: number;
}

interface Category {
  categoryId: number;
  mainCategory: string;
  subCategory: string;
  partTypes: PartTypes[];
}

interface PartTypes {
  partTypeId: number;
  position: string;
}

interface Units {
  memberId: string;
  nickname: string;
  score: number;
  unitDescription: string;
  unitImages: string[];
  positions: Positions[];
  age: number;
}

interface Positions {
  partTypeId: number;
  position: string;
}

interface Leader {
  memberId: string;
  nickname: string;
  profileImage: string;
  score: number;
}

export type { ProudctPreview, ProductListResponse };

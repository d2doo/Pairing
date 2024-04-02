import ProductTypeC from "./ProductTypeC";
import ProductTypeR from "./ProductTypeR";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { Button } from "@/components/ui/button.tsx";

export function PaginationDemo() {
  return (
    <Pagination>
      <PaginationContent>
        <PaginationItem>
          <PaginationPrevious href="#" />
        </PaginationItem>
        <PaginationItem>
          <PaginationLink href="#">1</PaginationLink>
        </PaginationItem>
        <PaginationItem>
          <PaginationLink href="#" isActive>
            2
          </PaginationLink>
        </PaginationItem>
        <PaginationItem>
          <PaginationLink href="#">3</PaginationLink>
        </PaginationItem>
        <PaginationItem>
          <PaginationEllipsis />
        </PaginationItem>
        <PaginationItem>
          <PaginationNext href="#" />
        </PaginationItem>
      </PaginationContent>
    </Pagination>
  );
}

function FindUnit() {
  return (
    <>
      <div className="p-2">
        <ProductTypeC />
        <div className="mt-8 border-y border-black1 py-4">
          <ProductTypeR
            onlyMyProduct={false}
            productId={0}
            isOnly={false}
            memberId=""
          />
          <PaginationDemo />
        </div>
        <div className="mb-14">
          <p className="m-3 font-GothicMedium text-black1">선택한 상품</p>
          <ProductTypeC />
          <div className="flex justify-end">
            <Button className="m-3 rounded">등록하기</Button>
          </div>
        </div>
      </div>
    </>
  );
}
export default FindUnit;

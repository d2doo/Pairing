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
} from "@/components/ui/pagination"
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
  )
}


function FindUnit() {
  return (
    <>
      <div className="p-2">
        <ProductTypeC />
        <div className="border-y border-black1 mt-8 py-4">
          <ProductTypeR />
          <PaginationDemo />
        </div>
        <div className="mb-14">
          <p
          className="font-GothicMedium text-black1 m-3">선택한 상품</p>
          <ProductTypeC />
          <div className="flex justify-end">
          <Button className="rounded m-3">등록하기</Button>
          </div>
          </div>      
      </div>
    </>
  );
}
export default FindUnit;

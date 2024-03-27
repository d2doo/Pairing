import { useState, useEffect } from "react";
import data from "@/assets/dummydata/productDetail_C.json";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Checkbox } from "@/components/ui/checkbox";
import { useParams } from "react-router-dom";
import {ImageUploadManager} from "@/components/ImageUploadManager.tsx";

interface Unit {
  memberId: string;
  nickname: string;
  positions: Positions[];
  age: number;
}

interface Positions {
  partTypeId: number;
  position: string;
}

interface Product {
  productId: number;
  leader: {
    memberId: string;
    nickname: string;
    profileImage: string;
  };
  productTitle: string;
  units: Unit[];
  totalPrice: number;
  averageAge: number;
}

const tableHead: string[] = ["카테고리", "사용기간", "판매자"];

function ProductDetailPage() {
  const { id } = useParams();
  const [item, setItem] = useState<Product>();

  useEffect(() => {
    (async () => {
      const json: Product = data;
      setItem(json);
    })();
  }, []);
  return (
    <>
      <div className="flex flex-col px-7 pb-14 pt-5 font-Gothic text-xs">
        <div className="flex flex-col  border-b border-gray1">
          <div className="flex flex-row space-x-px pb-2">
            <div className="flex h-4 w-12 items-center justify-center rounded-full border border-blue1">
              <p className="text-xxs">태그</p>
            </div>
            <div className="flex h-4 w-12 items-center justify-center rounded-full border border-blue1">
              <p className="text-xxs">태그 {id}</p>
            </div>
          </div>

          <p className="text-base">{item?.productTitle}</p>
        </div>
        <img src={item?.leader.profileImage} />

        <div className="flex flex-row space-x-1">
          <p>{item?.leader.nickname}</p>
          {item?.units.map((unit, index) =>
            unit.nickname !== item?.leader.nickname ? (
              <p key={index}>{unit.nickname}</p>
            ) : null,
          )}
        </div>
        <div className="flex justify-between pb-8">
          <p className="font-GothicBold text-base">{item?.totalPrice}원</p>
          <Button className="h-7 w-14 rounded-[0.625rem]">채팅하기</Button>
        </div>

        <Table className="font-Gothic">
          <TableCaption className="mb-8 mt-0.5 text-right font-GothicLight text-xs">
            위 표에서 정보를 볼 제품을 선택하세요
          </TableCaption>
          <TableHeader className="bg-blue1 ">
            <TableRow className="">
              <TableHead className="h-7 w-10"></TableHead>
              {tableHead.map((headText, index) => (
                <TableHead
                  key={index}
                  className="h-7 text-center font-Gothic text-black1"
                >
                  {headText}
                </TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody className="[&_tr:last-child]:border-1 text-center">
            {item?.units.map((unit) =>
              unit.positions.map((part, index) => (
                <TableRow key={index} className="h-7 border-b-blue1">
                  <TableCell className="p-0 ">
                    <Checkbox className="align-middle" />
                  </TableCell>
                  <TableCell className="p-0 font-Gothic text-xs">
                    {part.position}
                  </TableCell>
                  <TableCell className="p-0 font-Gothic text-xs">
                    {unit.age}개월
                  </TableCell>
                  <TableCell className="p-0 font-Gothic text-xs">
                    {unit.nickname}
                  </TableCell>
                </TableRow>
              )),
            )}
          </TableBody>
        </Table>

        <p>보고싶은 제품을 선택하면 이곳에서 설명을 볼 수 있어요 :)</p>
      </div>
    </>
  );
}

export default ProductDetailPage;

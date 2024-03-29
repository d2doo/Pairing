import { useState, useEffect } from "react";
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
import { Carousel, CarouselContent, CarouselItem, type CarouselApi } from "@/components/ui/carousel.tsx";
import { useParams } from "react-router-dom";
import {useLocalAxios} from "@/utils/axios.ts";
import {ProductDetailResponse} from "@/types/Product.ts";
import classNames from "classnames";

const tableHead: string[] = ["카테고리", "사용기간", "판매자"];

function ProductDetailPage() {
  const localAxios = useLocalAxios();
  const { id } = useParams();
  const [ item, setItem ] = useState<ProductDetailResponse>();
  const [ imageList, setImageList ] = useState<string[]>([]);
  const [ carouselApi, setCarouselApi ] = useState<CarouselApi>();
  const [ carouselCurrent, setCarouselCurrent ] = useState(0);
  const [ carouselTotal, setCarouselTotal ] = useState(0);

  useEffect(() => {
    if (id) getProductData(id);
  }, []);

  useEffect(() => {
    if (carouselApi) {
      setCarouselTotal(carouselApi.scrollSnapList().length);
      setCarouselCurrent(carouselApi.selectedScrollSnap());

      carouselApi.on("select", () => {
        setCarouselCurrent(carouselApi.selectedScrollSnap());
      });
    }
  }, [carouselApi]);

  const getProductData = async (productId: string) => {
    const response = await localAxios.get<ProductDetailResponse>(`/product/${productId}`);
    setItem(response.data);

    const newImageList = [];

    for (const unit of response.data.units) {
      for (const image of unit.unitImages) {
        newImageList.push(image);
      }
    }

    setImageList(newImageList);
  };

  const onClickBuyRequest = async () => {
    const response = localAxios.post(`/deal/buy/${item?.productId}`);

    // TODO: response 핸들링해서 상태값에 따라 상품 페이지 바꿔주기
  }

  return (
    <>
      <div className="flex flex-col px-7 pb-14 pt-5 font-Gothic text-xs space-y-3">
        <div className="flex flex-col border-b-2 border-gray1">
          <div className="flex flex-row space-x-px pb-2 gap-1">
            <div className="flex h-4 px-2 items-center justify-center rounded-full border border-blue1">
              <p className="text-xxs">{item?.category.mainCategory}</p>
            </div>
            <div className="flex h-4 px-2 items-center justify-center rounded-full border border-blue1">
              <p className="text-xxs">{item?.category.subCategory}</p>
            </div>
          </div>

          <p className="text-base font-semibold">{item?.productTitle}</p>
        </div>
        <div className="relative">
          <Carousel setApi={setCarouselApi}>
            <CarouselContent>
              {
                imageList.map(image => (
                  <CarouselItem key={image} className="aspect-square">
                    <img src={image} className="w-full h-full" />
                  </CarouselItem>
                ))
              }
            </CarouselContent>
          </Carousel>
          <div className='absolute bottom-6 flex gap-2 w-full justify-center'>
            {
              imageList.map((image, index) => (
                <div key={image} className={
                  classNames(
                      'rounded-full w-4 h-4 border-2 border-gray1',
                      (carouselCurrent == index ? 'bg-blue1' : 'bg-white1')
                  )
                }></div>
              ))
            }
          </div>
        </div>
        <div>
          <div className="flex flex-row space-x-1">
            <p>{item?.leader.nickname}</p>
            {item?.units.map((unit, index) =>
              unit.nickname !== item?.leader.nickname ? (
                <p key={index}>{unit.nickname}</p>
              ) : null,
            )}
          </div>
          <div className="flex justify-between items-center">
            <p className="font-GothicBold text-base">{item?.totalPrice.toLocaleString()} 원</p>
            <Button className="h-7 rounded-lg bg-white1 border-2 border-blue1 flex items-center gap-1 px-2 hover:bg-blue3"
                    onClick={onClickBuyRequest}>
              <span className="material-symbols-outlined text-[0.8rem]">chat</span>
              <span>구매 신청</span>
            </Button>
          </div>
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

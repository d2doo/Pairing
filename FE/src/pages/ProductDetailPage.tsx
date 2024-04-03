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
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  type CarouselApi,
} from "@/components/ui/carousel.tsx";
import { useNavigate, useParams } from "react-router-dom";
import { useLocalAxios } from "@/utils/axios.ts";
import { ProductDetailResponse, UnitResponse } from "@/types/Product.ts";
import classNames from "classnames";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { useAuthStore } from "@/stores/auth";

const tableHead: string[] = ["카테고리", "사용기간", "판매자"];

function ProductDetailPage() {
  const localAxios = useLocalAxios();
  const authStore = useAuthStore();
  const navigate = useNavigate();

  const { id } = useParams();
  const [item, setItem] = useState<ProductDetailResponse>();
  const [imageList, setImageList] = useState<string[]>([]);
  const [carouselApi, setCarouselApi] = useState<CarouselApi>();
  const [carouselCurrent, setCarouselCurrent] = useState(0);
  const [carouselTotal, setCarouselTotal] = useState(0);
  const [unitSelection, setUnitSelection] = useState<UnitResponse>();
  const [tableSelectionIndex, setTableSelectionIndex] = useState(0);

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
    const response = await localAxios.get<ProductDetailResponse>(
      `/product/${productId}`,
    );
    setItem(response.data);
    console.log(response.data);
    const newImageList = [];

    for (const unit of response.data.units) {
      for (const image of unit.unitImages) {
        newImageList.push(image);
      }
    }

    setImageList(newImageList);
  };

  const onClickBuyRequest = async () => {
    const response = await localAxios.put(`/deal/buy/${item?.productId}`);
    navigate("/chat");
    // TODO: response 핸들링해서 상태값에 따라 상품 페이지 바꿔주기
  };

  const onClickEditUnit = async () => {
    // TODO: 수정 구현
  };

  return (
    <>
      <div className="flex flex-col space-y-3 px-7 pb-14 pt-5 font-Gothic text-xs">
        <div className="flex flex-col border-b-2 border-gray1">
          <div className="flex flex-row gap-1 space-x-px pb-2">
            <div className="flex h-4 items-center justify-center rounded-full border border-blue1 px-2">
              <p className="text-xxs">{item?.category.mainCategory}</p>
            </div>
            <div className="flex h-4 items-center justify-center rounded-full border border-blue1 px-2">
              <p className="text-xxs">{item?.category.subCategory}</p>
            </div>
          </div>

          <p className="text-base font-semibold">{item?.productTitle}</p>
        </div>
        <div className="relative">
          <Carousel setApi={setCarouselApi}>
            <CarouselContent>
              {imageList.map((image, index) => (
                <CarouselItem key={index} className="aspect-square">
                  <img src={image} className="h-full w-full" />
                </CarouselItem>
              ))}
            </CarouselContent>
          </Carousel>
          <div className="absolute bottom-6 flex w-full justify-center gap-2">
            {imageList.map((image, index) => (
              <div
                key={index}
                className={classNames(
                  "h-4 w-4 rounded-full border-2 border-gray1",
                  carouselCurrent == index ? "bg-blue1" : "bg-white1",
                )}
              ></div>
            ))}
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
          <div className="flex items-center justify-between">
            <p className="font-GothicBold text-base">
              {item?.totalPrice.toLocaleString()} 원
            </p>
            {item?.units.find((x) => x.memberId == authStore.memberId) ? (
              <Button
                className="flex h-7 items-center gap-1 rounded-lg border-2 border-blue1 bg-white1 px-2 hover:bg-blue3"
                onClick={onClickEditUnit}
              >
                <span className="material-symbols-outlined text-[0.8rem]">
                  edit
                </span>
                <span>수정하기</span>
              </Button>
            ) : (
              <Button
                className="flex h-7 items-center gap-1 rounded-lg border-2 border-blue1 bg-white1 px-2 hover:bg-blue3"
                onClick={onClickBuyRequest}
              >
                <span className="material-symbols-outlined text-[0.8rem]">
                  chat
                </span>
                <span>구매 신청</span>
              </Button>
            )}
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
                <TableRow
                  key={part.partTypeId}
                  className="h-7 border-b-blue1"
                  onClick={() => {
                    setTableSelectionIndex(part.partTypeId);
                    setUnitSelection(unit);
                  }}
                >
                  <TableCell className="p-0">
                    <input
                      type="radio"
                      name="product-select"
                      className="align-middle"
                      checked={tableSelectionIndex == part.partTypeId}
                      onChange={() => {
                        setTableSelectionIndex(part.partTypeId);
                        setUnitSelection(unit);
                      }}
                    />
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
        {unitSelection ? (
          <p>{unitSelection.unitDescription}</p>
        ) : (
          <p>보고싶은 제품을 선택하면 이곳에서 설명을 볼 수 있어요 :)</p>
        )}
      </div>
    </>
  );
}

export default ProductDetailPage;

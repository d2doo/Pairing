// import React, { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";
import LeftImg from "@/assets/images/left-img.png";
import CaseImg from "@/assets/images/case-img.png";
import RightImg from "@/assets/images/right-img.png";
// import LeftRealImg from "@/assets/images/left-real.png";
// import RightRealImg from "@/assets/images/right-real.png";
// import CaseRealImg from "@/assets/images/case-real2.png";
import LeftRealImg2 from "@/assets/images/left-real2.png";
import RightRealImg2 from "@/assets/images/right-real2.png";
import CaseRealImg2 from "@/assets/images/case-real2.png";
// import ProductTypeC from "./ProductTypeC";
import { useEffect, useRef, useState } from "react";
import { Category } from "@/types/Category";
import { useLocalAxios } from "@/utils/axios";
import { PartType } from "@/types/PartType";
import { UnitResponse } from "@/types/Unit";
import UnitC from "./UnitC";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
// import { Input } from "./ui/input";
import Swal from "sweetalert2";
import { useLocation, useNavigate } from "react-router-dom";
import { ProductSaveRequest } from "@/types/Product";
import MultiClamp from "react-multi-clamp";

interface SaleProductProp {
  categoryId: number;
  unitprop: UnitCProps;
  product: ProductSaveRequest;
}
interface UnitCProps {
  unitId: number;
  images: string[];
  unitDescription: string;
  price: number;
  category: string;
  age: number;
  handler: (name: UnitCProps) => void;
  idx: number;
}

export default function SaleProduct() {
  const mapper = useRef<PartType[]>([]);
  const [chart, setChart] = useState(-1);
  const [title, setTitle] = useState("");
  const size = useRef(12);
  const [tagList, setTagList] = useState<UnitCProps[]>([]);
  const [complete, setComplete] = useState<Map<string, boolean>>(
    new Map<string, boolean>(),
  );
  const location = useLocation();
  const props: SaleProductProp = location.state?.props;
  const indexMapper = new Map<string, number>();
  //left right case - 0, 1, 2;
  const [clr, setClr] = useState<UnitCProps[]>([]);

  // const loadUnitByCategory() = {}
  const localAxios = useLocalAxios();
  //내꺼 미리 받아서 넣어놓기 못했어
  const baseSetting = (u: SaleProductProp) => {};

  useEffect(() => {
    indexMapper.set("왼쪽", 0);
    indexMapper.set("오른쪽", 1);
    indexMapper.set("케이스", 2);
    //여기 수정 필
    localAxios.get<Category>("/category/main/1").then((res) => {
      mapper.current = res.data.partTypes;
      const newComplete = new Map<string, boolean>();
      res.data.partTypes.forEach((elem) => {
        newComplete.set(elem.position, false);
      });
      if (props.unitprop) {
        const idx = indexMapper.get(props.unitprop.category);
        if (idx != undefined) {
          newComplete.set(props.unitprop.category, true);
          const nextClr = [...clr];
          nextClr[idx] = props.unitprop;
          setComplete(newComplete);
          setClr(nextClr);
        }
      }
      setComplete(newComplete);
      baseSetting(props);
    }); // 차피 products있어도 mapper 없으면 의미 없음.
  }, []);
  // useEffect(() => {
  //   // console.log("come", complete);
  // }, [complete]);
  //스크롤 처리 필요 size ref로 해두고 나중에 ㄱ
  useEffect(() => {
    setTagList([]);
    if (mapper.current.length > 0) {
      // console.log("mapper", mapper.current, "chart", chart);
      const pos = mapper.current[chart].position;
      setTitle(pos);
      localAxios
        .get<
          UnitResponse[]
        >(`/unit?size=${size.current}&&partTypeId=${mapper.current[chart].partTypeId}`)
        .then((res) => {
          res.data.forEach((elem) => {
            setTagList((prev) => [
              ...prev,
              {
                unitId: elem.unitId,
                images: elem.images,
                unitDescription: elem.unitDescription,
                price: elem.price,
                category: pos,
                age: elem.age,
                handler: UnitCOnClickHandler,
                idx: chart,
              },
            ]);
          });
        });
    }
  }, [chart]);

  const onHandleDrawerTriggerClick: (value: number) => void = (
    value: number,
  ) => {
    setChart(value);
  };

  const UnitCOnClickHandler = (props: UnitCProps) => {
    const nextComplete = new Map<string, boolean>(complete.entries());
    nextComplete.set(props.category, true);
    setComplete(nextComplete);

    const nextClr = [...clr];
    nextClr[props.idx] = props;
    setClr(nextClr);
  };
  useEffect(() => {
    // console.log("clr", clr);
  }, [clr]);

  const removeCompleteAndClr = (idx: number, category: string) => {
    const nextComplete = new Map<string, boolean>(complete.entries());
    nextComplete.set(category, false);
    setComplete(nextComplete);

    const nextClr = [...clr];
    nextClr[idx] = {
      unitId: -1,
      images: [],
      unitDescription: "",
      price: 0,
      category: "",
      age: 0,
      handler: () => {},
      idx: -1,
    };
    setClr(nextClr);
  };
  const navigate = useNavigate();
  const saveProduct = () => {
    Swal.fire({
      title: "저장하시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "저장하기",
    }).then(async (result) => {
      if (
        complete.get("케이스") &&
        complete.get("왼쪽") &&
        complete.get("오른쪽")
      ) {
        if (result.isConfirmed) {
          props.product.targetUnits = clr
            .filter((x) => x.idx != props.unitprop.idx)
            .map((elem) => elem.unitId);
          // console.log("sendData: ", props.product);
          const res = await localAxios.put(
            `/product/compose/${props.unitprop.unitId}`,
            props.product,
          );
          navigate("/category");
        }
      } else {
        Swal.fire("모든 제품을 등록해주세요.", "", "info");
      }
      /* Read more about isConfirmed, isDenied below */
    });
  };

  return (
    <>
      <div className="flex h-full flex-col items-center font-GothicLight">
        <div className="my-5 w-3/4 border-b-2 border-black1 pb-3 text-center font-GothicLight text-sm">
          <p>아래 아이콘들을 클릭해</p>
          <p>나만의 버즈를 조립해 보세요 :D</p>
          {/* 조립하기 시작하면 총 가격으로 바뀌게 가능? */}
        </div>
        <Drawer>
          <div className="m-4 flex h-full flex-col px-11 text-xs">
            <div className="flex-2 flex w-full animate-doong-sil flex-col items-center">
              {!complete.get("케이스") ? (
                <DrawerTrigger>
                  {/*여기 수정 필요*/}
                  <img
                    src={CaseImg}
                    alt="case_err"
                    onClick={() => onHandleDrawerTriggerClick(2)}
                  />
                </DrawerTrigger>
              ) : (
                <Popover>
                  <PopoverTrigger>
                    <img src={CaseRealImg2} />
                  </PopoverTrigger>
                  <PopoverContent>
                    <div className="flex flex-row justify-center gap-2 align-middle">
                      <img
                        src={clr[2].images[0]}
                        className="size-24 object-cover"
                      />
                      <div className="flex w-full rounded-lg border-2">
                        <div className="flex w-full flex-col self-center justify-self-center px-1 font-GothicLight text-sm">
                          <MultiClamp ellipsis="..." clamp={3}>
                            <div className="">{clr[2].unitDescription}</div>
                          </MultiClamp>
                          <div className="">가격: {clr[2].price}</div>
                          <div className="">연식: {clr[2].age}</div>
                          <div className="flex justify-center">
                            <Button
                              variant="outline"
                              className=""
                              onClick={() =>
                                removeCompleteAndClr(2, clr[2].category)
                              }
                            >
                              취소
                            </Button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </PopoverContent>
                </Popover>
              )}
            </div>

            <div className="flex items-center justify-between space-x-5">
              <div className="flex-1.5 flex w-full animate-doong-sil flex-col items-center justify-start">
                {!complete.get("왼쪽") ? (
                  <DrawerTrigger>
                    {/*여기 수정 필요*/}
                    <img
                      src={LeftImg}
                      alt="case_err"
                      onClick={() => onHandleDrawerTriggerClick(0)}
                    />
                  </DrawerTrigger>
                ) : (
                  <Popover>
                    <PopoverTrigger>
                      <img src={LeftRealImg2} />
                    </PopoverTrigger>
                    <PopoverContent>
                      <div className="flex flex-row justify-center gap-2 align-middle">
                        <img
                          src={clr[0].images[0]}
                          className="size-24 object-cover"
                        />
                        <div className="flex w-full rounded-lg border-2">
                          <div className="flex w-full flex-col self-center justify-self-center px-1 font-GothicLight text-sm">
                            <div className="">{clr[0].unitDescription}</div>
                            <div className="">가격: {clr[0].price}</div>
                            <div className="">연식: {clr[0].age}</div>
                            <div className="flex justify-center">
                              <Button
                                variant="outline"
                                className=""
                                onClick={() =>
                                  removeCompleteAndClr(0, clr[0].category)
                                }
                              >
                                취소
                              </Button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </PopoverContent>
                  </Popover>
                )}
              </div>
              <div className="flex-1.5 flex w-full animate-doong-sil flex-col items-center justify-end">
                {!complete.get("오른쪽") ? (
                  <DrawerTrigger>
                    {/*여기 수정 필요*/}
                    <img
                      src={RightImg}
                      alt="case_err"
                      onClick={() => onHandleDrawerTriggerClick(1)}
                    />
                  </DrawerTrigger>
                ) : (
                  <Popover>
                    <PopoverTrigger>
                      <img src={RightRealImg2} />
                    </PopoverTrigger>
                    <PopoverContent>
                      <div className="flex flex-row justify-center gap-2 align-middle">
                        <img
                          src={clr[1].images[0]}
                          className="size-24 object-cover"
                        />
                        <div className="flex w-full rounded-lg border-2">
                          <div className="flex w-full flex-col self-center justify-self-center px-1 font-GothicLight text-sm">
                            <div className="">{clr[1].unitDescription}</div>
                            <div className="">가격: {clr[1].price}</div>
                            <div className="">연식: {clr[1].age}</div>
                            <div className="flex justify-center">
                              <Button
                                variant="outline"
                                className=""
                                onClick={() =>
                                  removeCompleteAndClr(1, clr[1].category)
                                }
                              >
                                취소
                              </Button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </PopoverContent>
                  </Popover>
                )}
              </div>
            </div>
          </div>
          <DrawerContent>
            <div className="overflow-y-scroll">
              <DrawerHeader>
                <DrawerTitle>{title}</DrawerTitle>
                <DrawerDescription className="py-4">
                  {tagList.map((unit: UnitCProps) => {
                    return (
                      <DrawerClose>
                        <UnitC
                          key={unit.unitId}
                          unitId={unit.unitId}
                          images={unit.images}
                          unitDescription={unit.unitDescription}
                          price={unit.price}
                          category={unit.category}
                          age={unit.age}
                          handler={unit.handler}
                          idx={unit.idx}
                        />
                      </DrawerClose>
                    );
                  })}
                </DrawerDescription>
              </DrawerHeader>
            </div>
            <DrawerFooter>
              <Button size="drawer">선택</Button>
              <DrawerClose>
                <Button variant="outline">취소</Button>
              </DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
        <div className="mt-10">
          <Button size="md" onClick={saveProduct}>
            등록하기
          </Button>
        </div>
      </div>
    </>
  );
}

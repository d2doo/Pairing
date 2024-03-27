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
import ProductTypeC from "./ProductTypeC";

export default function SaleProduct() {
  return (
    <>
      <div className="flex h-full flex-col items-center font-GothicLight">
        <div className="my-5 w-3/4 border-b-2 border-black1 pb-3 text-center font-GothicLight text-sm">
          <p>아래 아이콘들을 클릭해</p>
          <p>나만의 버즈를 조립해 보세요 :D</p>
          {/* 조립하기 시작하면 총 가격으로 바뀌게 가능? */}
        </div>
        <Drawer>
          <div className="m-4 flex h-80 flex-col px-11 text-xs">
            <div className="flex-2 flex w-full animate-doong-sil flex-col items-center">
              <DrawerTrigger>
                <img src={CaseImg} alt="case_err" />
              </DrawerTrigger>
              {/* <div className="flex w-full flex-col items-center">
                <p>Case</p>
                <p>d2doo</p>
                <p>3000원</p>
              </div> */}
            </div>
            <div className="flex items-center justify-between space-x-5">
              <div className="flex-1.5 flex  w-full animate-doong-sil flex-col items-center justify-start">
                {/* <div>
                  <p>왼쪽</p>
                  <p>d1doo</p>
                  <p>3000원</p>
                </div> */}
                <DrawerTrigger>
                  <img src={LeftImg} alt="left_err" />
                </DrawerTrigger>
              </div>
              <div className="flex-1.5 flex w-full animate-doong-sil flex-col items-center justify-end">
                {/* <div>
                  <p>오른쪽</p>
                  <p>d3doo</p>
                  <p>3000원</p>
                </div> */}
                <DrawerTrigger>
                  <img src={RightImg} alt="right_err" />
                </DrawerTrigger>
              </div>
            </div>
          </div>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>케이스 | 왼쪽 | 오른쪽</DrawerTitle>
              <DrawerDescription className="py-4">
                <ProductTypeC />
                이거 반복해서 보여줘~~~
              </DrawerDescription>
            </DrawerHeader>
            <DrawerFooter>
              <Button size="drawer">선택</Button>
              <DrawerClose>
                <Button variant="outline">취소</Button>
              </DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
        <div className="mt-10">
          <Button size="md">등록하기</Button>
        </div>
      </div>
    </>
  );
}

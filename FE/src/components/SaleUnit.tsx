import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Textarea } from "./ui/textarea";
import { Label } from "./ui/label";

import React, { useState } from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";

import AddImage from "./AddImage";

// import {
//   Form,
//   FormControl,
//   FormDescription,
//   FormField,
//   FormItem,
//   FormLabel,
//   FormMessage,
// } from "@/components/ui/form";

const animatedComponents = makeAnimated();
// const colourOptions;
const categories: string[] = ["버즈", "버즈2"];
const partTypeIds: string[] = ["왼쪽", "오른쪽"];

const options = [
  { value: "왼쪽", label: "왼쪽" },
  { value: "오른쪽", label: "오른쪽" },
  { value: "케이스", label: "케이스" },
];

function SaleUnit() {
  return (
    <>
      <form className="font-Gothic text-xs">
        <div className="flex flex-col space-y-6 px-3 pb-8 pt-4">
          <div className="space-y-1.5">
            <Label>제목</Label>
            <Input
              className="h-9 rounded-xl border border-gray1 indent-3"
              placeholder="제목을 입력하세요."
            />
          </div>
          <div className="flex flex-col space-y-1.5">
            <Label>사진</Label>
            <AddImage />
          </div>
          <div className="flex flex-col space-y-1.5">
            <Label>분류</Label>

            <select className="rounded-lg border border-gray1">
              {categories.map((category, index) => (
                <option key={index}>{category}</option>
              ))}
            </select>
          </div>

          <div className="flex flex-col space-y-1.5">
            <Label>위치</Label>
            <Select
              closeMenuOnSelect={false}
              components={animatedComponents}
              isMulti
              options={options}
            />
          </div>
          <div className="flex flex-col space-y-1.5">
            <Label>사용기간</Label>
            <div className="flex items-center space-x-2">
              <Input
                placeholder="12"
                className="h-9 w-14 rounded-xl border-gray1 text-center"
              />
              <Label>개월</Label>
            </div>
          </div>
          <div className="space-y-1.5">
            <Label>가격</Label>
            <Input
              className="h-9 rounded-xl border-gray1 indent-3"
              type="number"
              placeholder="₩ 가격을 입력해주세요."
            />
          </div>
          <div className="space-y-1.5">
            <Label>설명</Label>
            <Textarea
              className="min-h-40 rounded-xl border border-gray1 text-left placeholder:text-gray1"
              placeholder="자세한 설명을 적어주세요:)"
            ></Textarea>
          </div>

          <div className="flex justify-center space-x-2 text-black1">
            <Button className="h-9 w-36 bg-blue1" type="submit">
              조립하지 않고 등록하기
            </Button>
            <Button className="h-9 w-36 bg-blue1" type="submit">
              조립하러가기
            </Button>
          </div>
        </div>
      </form>
    </>
  );
}

export default SaleUnit;

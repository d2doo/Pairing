import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Textarea } from "./ui/textarea";
import { Label } from "./ui/label";

import React, { useEffect, useState } from "react";
import Select from "react-select";
import makeAnimated from "react-select/animated";
import { useNavigate } from "react-router-dom";

import { ImageUploader } from "./ImageUploader";
import { AxiosResponse } from "axios";
import { useLocalAxios } from "@/utils/axios";
interface ImageUploadData {
  index: number;
  fileId: number;
}

interface TempCategoryResponse {
  // 여기 나중에 types/Product의 Category로 수정하기
  categoryId: number;
  mainCategory: string;
  subCategory: string;
  partTypes: TempPartTypeResponse[];
}

interface TempPartTypeResponse {
  // 이것도 저기있어
  partTypeId: number;
  position: string;
}

const animatedComponents = makeAnimated();

function SaleUnit() {
  const localAxios = useLocalAxios();
  const maximumImageCount = 5;
  const [imageIdx, setImageIdx] = useState<number>(1);
  const [imageList, setImageList] = useState<ImageUploadData[]>([
    {
      index: -1,
      fileId: -1,
    },
  ]);

  const imageRequestHandler = () => {
    setImageList([
      {
        index: imageIdx,
        fileId: -1,
      },
      ...imageList,
    ]);
    setImageIdx((prevIdx) => prevIdx + 1);
  };

  const imageCompleteHandler = (index: number, response: AxiosResponse) => {
    setImageList((prev) => {
      // 배열을 복사하여 새로운 배열을 생성
      const newList = prev.map((img) =>
        img.index === index
          ? {
              ...img,
              fileId: response.data.fileId, // 새로운 fileId로 업데이트
            }
          : img,
      );

      return newList;
    });
  };

  const imageRemoveHandler = (index: number) => {
    setImageList(imageList.filter((x) => x.index != index));
  };

  // 지수
  // 데이터 보낼 때()
  const navigate = useNavigate();
  const formSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    if (title.length === 0) {
      alert("제목을 입력해 주세요.");
    } else if (imageList.length === 1) {
      alert("사진을 첨부해 주세요.");
    } else if (selectedValues.length === 0) {
      alert("위치를 선택해 주세요.");
    } else if (age === undefined || age === null) {
      alert("사용기간을 입력해주세요.");
    } else if (!price) {
      alert("가격을 입력해 주세요.");
    } else if (describe.length === 0) {
      alert("상품 설명을 입력해 주세요.");
    } else {
      if (window.confirm("게시글을 등록하시겠습니까?")) {
        // console.log(imageList.map((x) => x.fileId));
        const images = imageList.map((x) => x.fileId);
        const sendImages = images.slice(1, images.length);
        console.log(sendImages);
        localAxios
          .post("/product", {
            productTitle: title,
            unit: {
              categoryId: category,
              isCombinable: true,
              unitDescription: describe,
              price,
              age,
              images: sendImages, // 파일 업로드시에 받은 이미지 ID
              status: "미사용", // (미사용, 사용감 없음, 사용감 적음, 사용감 많음)
              partTypeIds: selectedValues, // 카테고리 조회에서 얻은 왼, 오, 케에 대한 partType ID
            },
            targetUnits: [], // 묶을 애들
            thumbnailIndex: 0,
          })
          .then(function (response) {
            alert("상품이 등록되었습니다.");
            navigate("/category");
          })
          .catch(function (err) {
            console.log(err);
          });
      }
    }
  };

  const [categories, setCategories] = useState<TempCategoryResponse[]>([]);

  const [title, setTitle] = useState<string>(""); // 제목
  const [price, setPrice] = useState<number>(); // 가격
  const [category, setCategory] = useState<number>(); // 분류
  const [selectedValues, setSelectedValues] = useState<number[]>([]); // 위치
  const change = (selected) => {
    const newSelectedValues = selected.map((option) => option.value);
    setSelectedValues(newSelectedValues);
  };
  const [age, setAge] = useState<number>(); // 연식
  const [describe, setDescribe] = useState<string>(""); // 설명

  useEffect(() => {
    getCategories();
  }, []);

  const getCategories = async () => {
    const response = await localAxios.get("/category/sub");
    const responseData = response.data as TempCategoryResponse[];
    setCategories(responseData);
    setCategory(responseData[0].categoryId);
  };

  return (
    <>
      <div className="flex flex-col space-y-6 px-3 pb-8 pt-4 font-Gothic">
        <div className="space-y-1.5">
          <Label>제목</Label>
          <Input
            className="h-9 rounded-sm border border-gray1 indent-3"
            placeholder="제목을 입력하세요."
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
            }}
          />
        </div>
        <div className="flex flex-col space-y-1.5">
          <Label>사진</Label>
          <div className="flex ">
            {imageList.map((x) => (
              <ImageUploader
                key={x.index}
                onUploadRequested={imageRequestHandler}
                onUploadComplete={(response) => {
                  imageCompleteHandler(x.index, response);
                }}
                onRemove={() => {
                  imageRemoveHandler(x.index);
                }}
                disabled={imageList.length > maximumImageCount}
              >
                {imageList.length - 1} / {maximumImageCount}
              </ImageUploader>
            ))}
          </div>
        </div>
        <div className="flex flex-col space-y-1.5">
          <Label>분류</Label>

          <select
            className="rounded-sm border border-gray1 font-GothicLight text-xs"
            onChange={(e) => {
              setCategory(Number(e.target.value));
            }}
          >
            {categories.map((category, index) => (
              <option key={index} value={category.categoryId}>
                {category.subCategory}
              </option>
            ))}
          </select>
        </div>

        <div className="flex flex-col space-y-1.5">
          <Label>위치</Label>
          <Select
            closeMenuOnSelect={false}
            components={animatedComponents}
            isMulti
            options={categories
              .find((x) => x.categoryId == category)
              ?.partTypes.map((y) => {
                return {
                  value: y.partTypeId,
                  label: y.position,
                };
              })}
            placeholder="여러 개 선택 할 수 있어요"
            className="font-GothicLight text-xs"
            onChange={change}
          />
        </div>
        <div className="flex flex-col space-y-1.5">
          <Label>사용기간</Label>
          <div className="flex items-center space-x-2">
            <Input
              placeholder="12"
              className="h-9 w-14 rounded-sm border-gray1 text-center"
              value={age}
              onChange={(e) => {
                setAge(Number(e.target.value));
              }}
            />
            <Label>개월</Label>
          </div>
        </div>
        <div className="space-y-1.5">
          <Label>가격</Label>
          <Input
            className="h-9 rounded-sm border-gray1 indent-3"
            type="number"
            placeholder="₩ 가격을 입력해주세요."
            value={price}
            onChange={(e) => {
              setPrice(Number(e.target.value));
            }}
          />
        </div>
        <div className="space-y-1.5">
          <Label>설명</Label>
          <Textarea
            className="min-h-40 rounded-sm border border-gray1 text-left text-xs placeholder:text-gray1"
            value={describe}
            onChange={(e) => {
              setDescribe(e.target.value);
            }}
            placeholder="자세한 설명을 적어주세요:)"
          ></Textarea>
        </div>

        <div className="flex justify-center space-x-2 text-black1">
          <Button
            className="h-9 w-36 bg-blue1"
            type="submit"
            onClick={formSubmit}
          >
            조립하지 않고 등록하기
          </Button>
          <Button className="h-9 w-36 bg-blue1" type="submit">
            조립하러가기
          </Button>
        </div>
      </div>
    </>
  );
}

export default SaleUnit;

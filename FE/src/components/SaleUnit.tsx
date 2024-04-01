import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Textarea } from "./ui/textarea";
import { Label } from "./ui/label";

import React, { useEffect, useState } from "react";
import Select, { ActionMeta, MultiValue } from "react-select";
import makeAnimated from "react-select/animated";
import { useNavigate } from "react-router-dom";

import { ImageUploader } from "./ImageUploader";
import { AxiosResponse } from "axios";
import { useLocalAxios } from "@/utils/axios";
import { Category, ProductSaveRequest } from "@/types/Product";
import { UnitSaveRequest } from "@/types/Unit";
import Swal from "sweetalert2";
import { ImageRequest, ImageResponse } from "@/types/File";
interface ImageUploadData {
  index: number;
  fileId: number;
}

type ValueType<OptionType extends object> =
  | readonly OptionType[]
  | null
  | undefined;

interface SaleProductProp {
  categoryId: number;
  unitprop: UnitCProps;
  product:ProductSaveRequest,
}
interface UnitCProps {
  unitId: number;
  images: string[];
  unitDescription: string;
  price: number;
  category: string;
  age: number;
  idx: number;
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

  const navigate = useNavigate();

  const formSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    if (product.productTitle.length === 0) {
      alert("제목을 입력해 주세요.");
    } else if (imageList.length === 1) {
      alert("사진을 첨부해 주세요.");
    } else if (unit.partTypeIds.length === 0) {
      alert("위치를 선택해 주세요.");
    } else if ( unit.age === undefined || unit.age === null) {
      alert("사용기간을 입력해주세요.");
    } else if (!unit.price) {
      alert("가격을 입력해 주세요.");
    } else if (unit.unitDescription.length === 0) {
      alert("상품 설명을 입력해 주세요.");
    } else {
      Swal.fire({
        title: "제품을 등록하시겠습니까?",
        showCancelButton: true,
        confirmButtonText: "저장하기",
        denyButtonText: `취소`
      }).then((result) => {
        if (result.isConfirmed) {
          const images = imageList.map((x) => x.fileId);

        const sendImages = images.slice(1, images.length);
        unit.images=sendImages;
        product.unit = unit;
        unit.categoryId=category;
        localAxios.post('/product',product).then( res => {
          if( res.status == 200 ){
            Swal.fire("변경되었습니다.", "", "success");
          }else{
            Swal.fire("에러 발생! 잠시후 다시 시도해주세요.", "","error");
          }
        })
          
        } 
      });
    }
  };
  const [ unit, setUnit ] = useState< UnitSaveRequest>({
    categoryId: 0,
    isCombinable: true,
    unitDescription: '',
    price: 0,
    age: 0,
    images: [],// 파일 업로드시에 받은 이미지 ID
    status: '미사용',// (미사용, 사용감 없음, 사용감 적음, 사용감 많음)
    partTypeIds: [],// 카테고리 조회에서 얻은 왼, 오, 케에 대한 partType ID
  })
  const [ product, setProduct ] = useState<ProductSaveRequest>({
    productTitle: '',
      unit: unit,
      targetUnits: [], // 묶을 애들
      thumbnailIndex: 0,
  });
  

  const [categories, setCategories] = useState<Category[]>([]);
  const [category, setCategory ] = useState<number>(0);
 
  useEffect(() => {
    getCategories();
  }, []);

  const onChangedUpdateProduct = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProduct((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };


  const onChangedUpdateUnit = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUnit((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };

  const onChangedUpdateUnitSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setUnit((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };
  const onChangedUpdateUnitTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setUnit((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };

  const getCategories = async () => {
    const response = await localAxios.get("/category/sub");
    const responseData = response.data as Category[];
    console.log( responseData );
    setCategories(responseData);
    setCategory(responseData[0].categoryId);
  };
  
  const onChangeCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    console.log('hi');
    console.log(e);
    setCategory(Number(e.target.value));
  }
  useEffect(() => {
    console.log( category);
  },[category])

  const onChangeSelectedPartTypeIds = (selected: ValueType<{ value: number; label: string; }>, actionMeta: ActionMeta<{ value: number; label: string; }>) => {
    if (Array.isArray(selected)) {
        const newSelectedValues = selected.map((option) => option.value);
        setUnit((prev) => ({
            ...prev,
            isCombinable: newSelectedValues.length < 2,
            partTypeIds: newSelectedValues,
        }));
    }
};
  


  const openCompose = () => {
    Swal.fire({
      title: "조립하러 가시겠습니까?",
      showDenyButton: true,
      showCancelButton: true,
      confirmButtonText: "조립하러 가기",
      denyButtonText: `취소`
    }).then(async(result) => {
      if(unit.partTypeIds.length > 1 ){
        Swal.fire({
          icon: "error",
          title: "조립을 원하시면 제품을 하나만 등록해주세요.",
        });
      }else{
        if (result.isConfirmed) {
          const obj:ImageRequest = {
            imageIds: imageList.map( elem => elem.fileId ).slice( 1, imageList.length )
          }
          const res = await localAxios.post<ImageResponse[]>('/common/images',obj );
          const images = res.data.map( elem => elem.imgUrl);
          product.unit = unit;
          
          unit.images = imageList.map( elem => elem.fileId ).slice( 1, imageList.length );
          unit.categoryId=category;
          const part = categories[0].partTypes.filter( x => x.partTypeId === unit.partTypeIds[ 0 ] );
          const indexMapper = new Map<string, number>();
          indexMapper.set("왼쪽", 0);
          indexMapper.set("오른쪽", 1);
          indexMapper.set("케이스", 2);
          let idx: number = -1; // 기본값으로 0을 할당합니다.
          if (indexMapper) {
              if (part.length > 0) {
                  const position = part[0].position;
                  console.log( 'pos', position ,'re',indexMapper.get(position));
                  idx = indexMapper.get(position) || idx; // indexMapper에서 position에 대한 값이 없으면 기존 값인 idx를 사용합니다.
              }
          }
          
          //유닛 저장 해야댐
          const unitprop: UnitCProps = {
            unitId: -1,
            images: images,
            unitDescription: unit.unitDescription,
            price:unit.price,
            category: part[0].position,
            age:unit.age,
            idx: idx,
          };
          const response = await localAxios.post('/product', product );
          
          unitprop.unitId = response.data;
          const props: SaleProductProp = {
            categoryId: category,
            unitprop: unitprop,
            product: product,
          };

          
          
          
          if( response.status == 200 ){
            navigate("/new/product", { state: { props: props } });
          }
        };
      }
      
    });
  }
  
  return (
    <>
      <div className="flex flex-col space-y-6 px-3 pb-8 pt-4 font-Gothic">
        <div className="space-y-1.5">
          <Label>제목</Label>
          <Input
            className="h-9 rounded-sm border border-gray1 indent-3"
            placeholder="제목을 입력하세요."
            value={product.productTitle}
            name='productTitle'
            onChange={onChangedUpdateProduct}
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
            onChange={onChangeCategory}
            value={category}
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
            onChange={onChangeSelectedPartTypeIds}
          />
        </div>
        <div className="flex flex-col space-y-1.5">
          <Label>사용기간</Label>
          <div className="flex items-center space-x-2">
            <Input
              placeholder="12"
              className="h-9 w-14 rounded-sm border-gray1 text-center"
              value = { unit.age }
              name = 'age'
              onChange={ onChangedUpdateUnit }
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
            value={unit.price}
            name = 'price'
            onChange={onChangedUpdateUnit}
          />
        </div>
        <div className="space-y-1.5">
          <Label>상태</Label>
          <Input
            className="h-9 rounded-sm border-gray1 indent-3"
            type="text"
            placeholder="제품 상태를 입력해주세요."
            value={unit.status}
            name = 'status'
            onChange={onChangedUpdateUnit}
          />
        </div>
        <div className="space-y-1.5">
          <Label>설명</Label>
          <Textarea
            className="min-h-40 rounded-sm border border-gray1 text-left text-xs placeholder:text-gray1"
            value={unit.unitDescription}
            name = 'unitDescription'
            onChange={onChangedUpdateUnitTextArea}
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
          <Button
            className="h-9 w-36 bg-blue1"
            type="button"
            onClick={openCompose}
          >
            조립하러가기
          </Button>
        </div>
      </div>
    </>
  );
}

export default SaleUnit;

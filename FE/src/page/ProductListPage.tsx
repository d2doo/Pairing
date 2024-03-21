import { useState, useEffect } from "react";
import ProductTypeR from "@/components/ProductTypeR";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const categoryList: string[] = ["전체", "버즈", "에어팟", "조이콘"];
const subList1: string[] = ["전체", "케이스", "왼쪽", "오른쪽"];
// const subList2: string[] = ["전체", "케이스", "왼쪽"];

function ProductListPage() {
  const [selectType, setSetlectType] = useState(String);

  useEffect(() => {
    console.log(selectType);
  }, [selectType]);

  async function clickSelectType(type: string) {
    await setSetlectType(type);
    console.log(type);
  }
  return (
    <>
      <Tabs defaultValue="individual" className="w-full p-0 m-0 font-Gothic">
        <TabsList className="grid w-full grid-cols-2 p-0 m-0 text-black1 border-b-black1 border-b">
          <TabsTrigger
            value="individual"
            className="h-full
          data-[state=active]:bg-black data-[state=active]:text-white1 "
          >
            개별제품
          </TabsTrigger>
          <TabsTrigger
            value="combine"
            className="h-full
            data-[state=active]:bg-black data-[state=active]:text-white1"
          >
            조합제품
          </TabsTrigger>
        </TabsList>
        <TabsContent value="individual">
          <Tabs defaultValue="전체" className="w-full">
            <TabsList className="grid w-full grid-cols-4 p-0 mb-2 border-b-gray1 border-b">
              {categoryList.map((category, index) => (
                <TabsTrigger
                  key={index}
                  value={category}
                  className="h-full
                 data-[state=active]:text-black1 data-[state=active]:border-b-black1 data-[state=active]:border-b-2"
                >
                  {category}
                </TabsTrigger>
              ))}
            </TabsList>
            {categoryList.map((category, index) => (
              <TabsContent key={index} value={category}>
                {category === "전체" ? (
                  <p className="flex items-center font-GothicLight text-xs pl-7 pb-3">
                    전체
                  </p>
                ) : (
                  <div className="flex items-center font-GothicLight text-xs pl-7 pb-3">
                    <p className="pr-3">
                      {category}&nbsp;&nbsp;&nbsp;&nbsp;{">"}
                    </p>
                    <Select
                      defaultValue="전체"
                      onValueChange={(value) => clickSelectType(value)}
                    >
                      <SelectTrigger className="w-20 h-5">
                        <SelectValue placeholder="전체" />
                      </SelectTrigger>
                      <SelectContent>
                        {subList1.map((sub, index) => (
                          <SelectItem key={index} value={sub}>
                            {sub}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                )}
                <ProductTypeR />
              </TabsContent>
            ))}
          </Tabs>
        </TabsContent>
        <TabsContent value="combine">
          <Tabs defaultValue="전체" className="w-full">
            <TabsList className="grid w-full grid-cols-4 p-0 mb-2 border-b-gray1 border-b">
              {categoryList.map((category, index) => (
                <TabsTrigger
                  key={index}
                  value={category}
                  className="h-full
                 data-[state=active]:text-black1 data-[state=active]:border-b-black1 data-[state=active]:border-b-2"
                >
                  {category}
                </TabsTrigger>
              ))}
            </TabsList>
            {categoryList.map((category, index) => (
              <TabsContent key={index} value={category}>
                {category === "전체" ? (
                  <p className="flex items-center font-GothicLight text-xs pl-7 pb-3">
                    전체
                  </p>
                ) : (
                  <div className="flex items-center font-GothicLight text-xs pl-7 pb-3">
                    <p className="pr-3">
                      {category}&nbsp;&nbsp;&nbsp;&nbsp;{">"}
                    </p>
                    <Select
                      defaultValue="전체"
                      onValueChange={(value) => clickSelectType(value)}
                    >
                      <SelectTrigger className="w-20 h-5">
                        <SelectValue placeholder="전체" />
                      </SelectTrigger>
                      <SelectContent>
                        {subList1.map((sub, index) => (
                          <SelectItem key={index} value={sub}>
                            {sub}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                )}
                <ProductTypeR />
              </TabsContent>
            ))}
          </Tabs>
        </TabsContent>
      </Tabs>
    </>
  );
}

export default ProductListPage;

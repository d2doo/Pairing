import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ProductTypeR from "@/components/ProductTypeR";
import ProductDetailPage from "./ProductDetailPage";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

import { QueryClientProvider } from "react-query";
import { queryClient } from "@/utils/queryClient";

const categoryList: string[] = ["전체", "버즈", "에어팟", "조이콘"];
const subList1: string[] = ["전체", "케이스", "왼쪽", "오른쪽"];

function ProductListPage() {
  const navigate = useNavigate();
  const [isCombined, setIsCombined] = useState(false);
  const [selectType, setSelectType] = useState("");
  useEffect(() => {
    console.log(selectType);
  }, [selectType]);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <Tabs defaultValue="individual" className="m-0 w-full p-0 font-Gothic">
          <TabsList className="m-0 grid w-full grid-cols-2  border-b border-b-black1 p-0 text-black1">
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
              <TabsList className="mb-2 grid w-full grid-cols-4 border-b border-b-gray1 p-0">
                {categoryList.map((category, index) => (
                  <TabsTrigger
                    key={index}
                    value={category}
                    className="h-full
                 data-[state=active]:border-b-2 data-[state=active]:border-b-black1 data-[state=active]:text-black1"
                  >
                    {category}
                  </TabsTrigger>
                ))}
              </TabsList>
              {categoryList.map((category, index) => (
                <TabsContent key={index} value={category}>
                  {category === "전체" ? (
                    <p className="flex items-center pb-3 pl-7 font-GothicLight text-xs">
                      전체
                    </p>
                  ) : (
                    <div className="flex items-center pb-3 pl-7 font-GothicLight text-xs">
                      <p className="pr-3">
                        {category}&nbsp;&nbsp;&nbsp;&nbsp;{">"}
                      </p>

                      <select className=" m-1 h-5 w-20 py-0.5 pl-1 text-xs">
                        {subList1.map((sub, index) => (
                          <option className="" key={index} value={sub}>
                            <p>{sub}</p>
                          </option>
                        ))}
                      </select>
                      {/* <Select
                      defaultValue="전체"
                      onValueChange={(value) => clickSelectType(value)}
                    >
                      <SelectTrigger className="h-5 w-20">
                        <SelectValue placeholder="전체" />
                      </SelectTrigger>
                      <SelectContent>
                        {subList1.map((sub, index) => (
                          <SelectItem key={index} value={sub}>
                            {sub}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select> */}
                    </div>
                  )}
                  <ProductTypeR onlyMyProduct={false} productId={0} />
                </TabsContent>
              ))}
            </Tabs>
          </TabsContent>
          <TabsContent value="combine">
            <Tabs defaultValue="전체" className="w-full">
              <TabsList className="mb-2 grid w-full grid-cols-4 border-b border-b-gray1 p-0">
                {categoryList.map((category, index) => (
                  <TabsTrigger
                    key={index}
                    value={category}
                    className="h-full
                 data-[state=active]:border-b-2 data-[state=active]:border-b-black1 data-[state=active]:text-black1"
                  >
                    {category}
                  </TabsTrigger>
                ))}
              </TabsList>
              {categoryList.map((category, index) => (
                <TabsContent
                  key={index}
                  value={category}
                  // onClick={() => handleTabClick(category)}
                >
                  {category === "전체" ? (
                    <p className="flex items-center pb-3 pl-7 font-GothicLight text-xs">
                      전체
                    </p>
                  ) : (
                    <div className="flex items-center pb-3 pl-7 font-GothicLight text-xs">
                      <p className="pr-3">
                        {category}&nbsp;&nbsp;&nbsp;&nbsp;{">"}
                      </p>
                      <select className="m-1 flex h-5 w-20 rounded-xl py-0.5 pl-1 ">
                        {subList1.map((sub, index) => (
                          <option className="" key={index} value={sub}>
                            {sub}
                          </option>
                        ))}
                      </select>
                    </div>
                  )}
                  {/* <ProductTypeR /> */}
                </TabsContent>
              ))}
            </Tabs>
          </TabsContent>
        </Tabs>
      </QueryClientProvider>
    </>
  );
}

export default ProductListPage;

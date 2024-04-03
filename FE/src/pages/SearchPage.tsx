import { useKeywordStore } from "@/stores/search";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@radix-ui/react-tabs";
import { useEffect, useState } from "react";

import { useLocalAxios } from "@/utils/axios";
import {
  ProductFindResponse,
  ProductFindResponseTwo,
  ProudctPreview,
} from "@/types/Product";
import { Link } from "react-router-dom";
import SearchBar from "@/components/SearchBar";
const SearchPage = () => {
  function Product({
    thumbnailUrl,
    category,
    productId,
    productTitle,
    totalPrice,
  }: ProudctPreview) {
    return (
      <>
        <Link to={"/product/" + productId} key={productId}>
          <img src={thumbnailUrl} className="size-32 object-cover pb-1" />
          <div className="flex flex-row space-x-px pb-0.5">
            {category.map((temp, index) => (
              <div
                key={index}
                className="flex h-4 w-12 items-center justify-center rounded-full border border-blue1 "
              >
                <p className="font-Gothic text-xxs">{temp}</p>
              </div>
            ))}
          </div>
          <p className="truncate pb-0.5 font-Gothic text-xs">{productTitle}</p>
          <p className="font-GothicBold text-xs">
            {totalPrice.toLocaleString()}원
          </p>
        </Link>
      </>
    );
  }
  const keywordStore = useKeywordStore();
  const localAxios = useLocalAxios();
  const [products, setProducts] = useState<ProductFindResponseTwo[]>();
  useEffect(() => {
    if (keywordStore.keyword) {
      FetchingSearchByKeyword(keywordStore.keyword);
    }
  }, [keywordStore.keyword]);

  const FetchingSearchByKeyword = async (keyword: string) => {
    const res = await localAxios<ProductFindResponseTwo[]>(
      `/product?size=100&&keyword=${keyword}`,
    );
    console.log(res);
    setProducts(res.data);
  };
  return (
    <>
      <SearchBar />
      <Tabs defaultValue="search" className="m-0 w-full p-0 font-Gothic">
        {/* <div
                className={`header transition-all duration-300 ${isSticky ? "sticky top-0 z-50 w-full bg-white" : ""}`}
              > */}
        <TabsList className="m-0 grid h-10 w-full grid-cols-1 border-b  border-b-black1 p-0 font-Gothic text-black1">
          <TabsTrigger
            value="search"
            className="h-full w-full
              data-[state=active]:bg-black data-[state=active]:text-white1 "
          >
            검색결과
          </TabsTrigger>
        </TabsList>
        {/* </div> */}
        <TabsContent value="search" className="h-screen overflow-scroll">
          <div className="flex flex-col px-7 py-7">
            <div className="grid grid-cols-2 gap-x-10 gap-y-7">
              {products &&
                products?.length > 0 &&
                products?.map((p) => {
                  return (
                    <Product
                      key={p.productId}
                      thumbnailUrl={p.thumbnailUrl}
                      category={p.units
                        .map((unit) => {
                          //   console.log("unit:", unit.positions);
                          //   console.log(unit.positions.position);
                          return unit.positions
                            .map((p) => p.position)
                            .map((s) => s);
                        })
                        .flat()}
                      productId={p.productId}
                      productTitle={p.productTitle}
                      totalPrice={p.totalPrice}
                    />
                  );
                })}
              {products?.length == 0 && (
                <div className="w-screen"> 찾으시는 결과가 없습니다. </div>
              )}

              {/* <Product
                key={index}
                thumbnailUrl={item.thumbnailUrl}
                category={item.category}
                productId={item.productId}
                productTitle={item.productTitle}
                totalPrice={item.totalPrice}
              /> */}
            </div>
          </div>
        </TabsContent>
      </Tabs>
    </>
  );
};

export default SearchPage;

// import { useEffect, useRef, useState } from "react";
import ProductTypeR from "@/components/ProductTypeR";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { QueryClientProvider } from "react-query";
import { queryClient } from "@/utils/queryClient";

function ProductListPage() {
  // const [isSticky, setIsSticky] = useState(false);

  // useEffect(() => {
  //   const handleScroll = () => {
  //     // window.scrollY를 사용하여 스크롤 위치에 따른 로직 처리
  //     setIsSticky(window.scrollY > 100);
  //     console.log(window.scrollY);
  //   };

  //   window.addEventListener("scroll", handleScroll);

  //   return () => {
  //     window.removeEventListener("scroll", handleScroll);
  //   };
  // }, []);

  return (
    <>
      <QueryClientProvider client={queryClient}>
        <Tabs defaultValue="individual" className="m-0 w-full p-0 font-Gothic">
          {/* <div
            className={`header transition-all duration-300 ${isSticky ? "sticky top-0 z-50 w-full bg-white" : ""}`}
          > */}
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
          {/* </div> */}
          <TabsContent value="individual">
            <ProductTypeR
              params={{
                isOnly: true,
                size: 6,
              }}
              tabName="individual"
            />
          </TabsContent>
          <TabsContent value="combine">
            <ProductTypeR
              params={{
                isOnly: false,
                size: 6,
              }}
              tabName="combine"
            />
          </TabsContent>
        </Tabs>
      </QueryClientProvider>
    </>
  );
}

export default ProductListPage;

import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ProductTypeR from "@/components/ProductTypeR";
import ProductDetailPage from "./ProductDetailPage";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

import { QueryClientProvider } from "react-query";
import { queryClient } from "@/utils/queryClient";

function ProductListPage() {
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
            <ProductTypeR onlyMyProduct={false} productId={0} />
          </TabsContent>
          <TabsContent value="combine">
            <ProductTypeR onlyMyProduct={false} productId={0} />
          </TabsContent>
        </Tabs>
      </QueryClientProvider>
    </>
  );
}

export default ProductListPage;

import { useMemo } from "react";
import { QueryFunctionContext, useInfiniteQuery } from "react-query";
import { ProductListResponse } from "@/types/Products.ts";

interface useSearchProductQueryProps {
  rowsPerPage: number;
  queryFn: (context?: QueryFunctionContext) => Promise<ProductListResponse[]>;
}

const queryKey = "searchProducts";

const useSearchProductQuery = ({
  rowsPerPage,
  queryFn,
}: useSearchProductQueryProps) => {
  const { data, isLoading, isError, fetchNextPage, isFetchingNextPage } =
    useInfiniteQuery(queryKey, queryFn, {
      getNextPageParam: (lastPage, allPages) => {
        const nextPage = allPages.length + 1;

        //상품이 0개이거나 rowsPerPage보다 작을 경우 스크롤을 막는다.
        return allPages.length === 0 || allPages.length < rowsPerPage
          ? undefined
          : nextPage;
      },
      retry: 0,
      refetchOnMount: false,
      refetchOnReconnect: false,
      refetchOnWindowFocus: false,
    });

  const products = useMemo(() => {
    // 상품 컴포넌트(ProductCard.tsx)의 props에 맞춰 데이터 가공처리
    if (data === undefined) return data;
    const response = data.pages[0];
    const json = response.map((item) => ({
      thumbnailUrl: item.thumbnailUrl,
      category: item.category.partTypes.map((part) => part.position), // partTypes 배열에서 position만 추출하여 category 배열로 변환
      productId: item.productId.toString(), // productId를 문자열로 변환
      productTitle: item.productTitle,
      totalPrice: item.totalPrice,
    }));
    return json;
  }, [data]);

  return { products, isLoading, isError, fetchNextPage, isFetchingNextPage };
};

export default useSearchProductQuery;

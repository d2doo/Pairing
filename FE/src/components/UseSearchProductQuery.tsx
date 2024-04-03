import { useMemo } from "react";
import { QueryFunctionContext, useInfiniteQuery } from "react-query";
import {
  ProductDetailResponse,
  ProductRequestParams,
} from "@/types/Product.ts";

interface useSearchProductQueryProps {
  rowsPerPage: number;
  query: string;
  params: ProductRequestParams;
  queryFn: (context?: QueryFunctionContext) => Promise<ProductDetailResponse[]>;
}

const useSearchProductQuery = ({
  rowsPerPage,
  queryFn,
  query,
}: useSearchProductQueryProps) => {
  // 탭별 구분을 주기 위함
  const queryKey = useMemo(() => ["searchProducts", { query }], [query]);

  const {
    data,
    refetch,
    isLoading,
    isError,
    fetchNextPage,
    isFetchingNextPage,
  } = useInfiniteQuery(queryKey, queryFn, {
    getNextPageParam: (lastPage, allPages) => {
      const nextPage = allPages.length + 1;
      //상품이 0개이거나 rowsPerPage보다 작을 경우 스크롤을 막는다.
      return allPages[0].length === 0 || lastPage.length < rowsPerPage
        ? undefined
        : nextPage;
    },
    cacheTime: 0,
    retry: 0,
    refetchOnMount: true,
    refetchOnReconnect: false,
    refetchOnWindowFocus: false,
  });

  const products = useMemo(() => {
    // 상품 컴포넌트(ProductCard.tsx)의 props에 맞춰 데이터 가공처리
    if (data === undefined) return data;
    // 모든 배열을 단일 배열로 flat 처리
    const response = data.pages.flat();
    // console.log("response: ", response);
    // console.log(data);
    const json = response.map((item) => ({
      thumbnailUrl: item.thumbnailUrl,
      //partTypes 배열에서 position만 추출하여 category 배열로 변환
      category: item.units
        .map((unit) => unit.positions.map((part) => part.position))
        .flat(),
      productId: item.productId.toString(), // productId를 문자열로 변환
      productTitle: item.productTitle,
      totalPrice: item.totalPrice,
    }));
    return json;
  }, [data]);

  return {
    products,
    refetch,
    isLoading,
    isError,
    fetchNextPage,
    isFetchingNextPage,
  };
};

export default useSearchProductQuery;

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useSearchProductQuery from "./UseSearchProductQuery";
import { useInView } from "react-intersection-observer";
import { useLocalAxios } from "@/utils/axios.ts";
import SkeletonCard from "./SkeletonCard";

import {
  ProudctPreview,
  ProductDetailResponse,
  ProductRequestParams,
} from "@/types/Product";

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

function ProductTypeR(props: {
  params: ProductRequestParams;
  tabName: string;
}) {
  // 제품 조회는 권한이 필요 없으므로 false
  const localAxios = useLocalAxios(false);

  const PARAM = props.params;
  const params = props.params;
  const ROWS_PER_PAGE = 6;
  const [productList, setProductList] = useState<ProudctPreview[]>();
  const [lastProductId, setLastProductId] = useState<number>(0);

  // 페이지에 들어오면 기존 데이터 리셋
  useEffect(() => {
    params.productId = undefined;
    console.log("들어왔따.");
    console.log(params);
    setLastProductId(0);
  }, [props.tabName]);

  const { products, isLoading, isError, fetchNextPage, isFetchingNextPage } =
    useSearchProductQuery({
      // startCount: 몇번째 상품부터 불러올건지 시작인덱스 / row: 받아 올 상품 개수
      rowsPerPage: ROWS_PER_PAGE,
      query: props.tabName,
      params: params,
      queryFn: () => fetchProductsData(),
    });
  console.log(products);
  const fetchProductsData = async (): Promise<ProductDetailResponse[]> => {
    // params를 받아서 처리
    const response = await localAxios.get<ProductDetailResponse[]>(`/product`, {
      params: params,
    });
    return response.data;
  };

  const { ref, inView } = useInView({
    threshold: 0, // 여기서 원하는 threshold 값을 설정
    delay: 500,
  });

  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView, fetchNextPage]);

  useEffect(() => {
    // product 확인 후 마지막 proudctId 가져와서 데이터 변경
    if (products && products.length !== 0) {
      setProductList(products); // Test
      const last = Number(products[products.length - 1].productId);
      setLastProductId(last);
      params.productId = last;
    }
  }, [products, lastProductId]);

  if (isLoading) {
    return (
      <div className={"products"}>
        <SkeletonCard size={ROWS_PER_PAGE} />
      </div>
    );
  }

  if (isError) {
    return <></>;
  }

  return (
    <>
      {products?.length === 0 ? (
        <div className="pt-10 text-center font-GothicBold text-3xl text-gray1">
          검색 결과 없음
        </div>
      ) : (
        <div className="flex flex-col px-7 py-7">
          <div className="grid grid-cols-2 gap-x-10 gap-y-7">
            {products?.map((item: ProudctPreview, index) => (
              <Product
                key={index}
                thumbnailUrl={item.thumbnailUrl}
                category={item.category}
                productId={item.productId}
                productTitle={item.productTitle}
                totalPrice={item.totalPrice}
              />
            ))}
          </div>
          {isFetchingNextPage ? (
            <SkeletonCard size={ROWS_PER_PAGE} />
          ) : (
            <div ref={ref} />
          )}
        </div>
      )}
    </>
  );
}

export default ProductTypeR;

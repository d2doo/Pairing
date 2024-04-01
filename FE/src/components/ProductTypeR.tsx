import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useSearchProductQuery from "./UseSearchProductQuery";
import { useInView } from "react-intersection-observer";
import { useLocalAxios } from "@/utils/axios.ts";
import SkeletonCard from "./SkeletonCard";
import { ProudctPreview, ProductDetailResponse } from "@/types/Product";

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
        <p className="font-GothicBold text-xs">{totalPrice}원</p>
      </Link>
    </>
  );
}

function Products(props: { onlyMyProduct: boolean; productId: number }) {
  const localAxios = useLocalAxios(props.onlyMyProduct); // 로그인 필요 없을 때 사용
  const ROWS_PER_PAGE = 4;
  const [lastProductId, setLastProductId] = useState<number>(props.productId);
  const { products, isLoading, isError, fetchNextPage, isFetchingNextPage } =
    useSearchProductQuery({
      // startCount: 몇번째 상품부터 불러올건지 시작인덱스 / row: 받아 올 상품 개수
      rowsPerPage: ROWS_PER_PAGE,
      queryFn: () => fetchProductsData(ROWS_PER_PAGE, lastProductId),
    });

  // 처음 요청은 size만 받고 그 다음 요청을 할 때는 productId가 필요
  const fetchProductsData = async (
    size: number,
    productId: number,
  ): Promise<ProductDetailResponse[]> => {
    const params =
      productId !== 0 ? { size: size, productId: productId } : { size: size };

    const response = await localAxios.get<ProductDetailResponse[]>(`/product`, {
      params: params,
    });
    return response.data;
  };

  const { ref, inView } = useInView();

  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView]);

  useEffect(() => {
    // product 확인
    // 마지막 proudctId 가져오기
    if (products && products.length !== 0) {
      const last = Number(products[products.length - 1].productId);
      setLastProductId(last);
    }
  }, [products, props.productId]);

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
      <div className="flex flex-wrap px-7 ">
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
    </>
  );
}

export default Products;

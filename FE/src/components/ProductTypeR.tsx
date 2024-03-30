import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useSearchProductQuery from "./UseSearchProductQuery";
import { useInView } from "react-intersection-observer";
import { ProudctPreview, ProductListResponse } from "@/types/Products.ts";
import { useLocalAxios } from "@/utils/axios.ts";
import SkeletonCard from "./SkeletonCard";

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

function Products() {
  const localAxios = useLocalAxios(false); // 로그인 필요 없을 때 사용
  const ROWS_PER_PAGE = 10;
  const [lastProductId, setLastProductId] = useState<number>(1);
  const [productList, setProductList] = useState<ProudctPreview[]>([]);
  const { products, isLoading, isError, fetchNextPage, isFetchingNextPage } =
    useSearchProductQuery({
      // startCount: 몇번째 상품부터 불러올건지 시작인덱스 / row: 받아 올 상품 개수
      rowsPerPage: ROWS_PER_PAGE,
      queryFn: () => fetchProductsData(ROWS_PER_PAGE, lastProductId),
    });

  const fetchProductsData = async (
    size: number,
    productId: number,
  ): Promise<ProductListResponse[]> => {
    const response = await localAxios.get<ProductListResponse[]>(`/product`, {
      params: { size: size, productId: productId },
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
    if (products) {
      setLastProductId(Number(products[products.length - 1].productId));
      setProductList([...productList, ...products]);
    }
  }, [products]);

  if (isLoading) {
    return (
      <div className={"productList"}>
        <SkeletonCard />
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
          {productList?.map((item: ProudctPreview, index) => (
            <Product
              key={index}
              thumbnailUrl={item.thumbnailUrl}
              category={item.category}
              productId={item.productId}
              productTitle={item.productTitle}
              totalPrice={item.totalPrice}
            />
          ))}
          {isFetchingNextPage ? <SkeletonCard /> : <div ref={ref} />}
        </div>
      </div>
    </>
  );
}

export default Products;

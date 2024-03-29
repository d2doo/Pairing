// import Products from "@/components/ProductTypeR";
import { useAuthStore } from "@/stores/auth";
import { ProductFindResponse } from "@/types/Product";
import { useLocalAxios } from "@/utils/axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function InfoSale() {
  const localAxios = useLocalAxios();
  const auth = useAuthStore();

  const [products, setProduct] = useState<ProductFindResponse[]>([]);
  useEffect(() => {
    ProductsSetting();
  }, []);

  const ProductsSetting = async () => {
    const result = await localAxios.get<ProductFindResponse[]>(
      `/product?page=0&&size=1`,
    );
    const json = result.data;
    console.log(json);
    setProduct(json);
  };

  return (
    <>
      <div className="flex w-full flex-col items-center font-Gothic">
        <div className="m-10 w-2/3 text-sm">
          <div className="flex justify-center">
            <div className="flex justify-between space-x-6">
              <div>
                <img
                  src={auth.profileImage}
                  className="size-28 rounded-full object-cover"
                />
              </div>
              <div className="flex flex-col justify-center">
                <p>
                  <span className="font-GothicBold">{auth.nickname}</span>님의
                  거래 신뢰도는
                </p>
                <p>
                  <span className="font-GothicBold">{auth.score}</span> 입니다.
                </p>
                <p className="my-4">오늘도 즐거운 쇼핑되세요 :D</p>
              </div>
            </div>
          </div>
        </div>
        <div className="border-y py-6">
          {products.map((item: ProductFindResponse, index) => (
            <Product
              key={index}
              thumbnailUrl={item.thumbnailUrl}
              category={item.category}
              productId={item.productId}
              productTitle={item.productTitle}
              totalPrice={item.totalPrice}
              leader={item.leader}
              maxAge={item.maxAge}
              units={item.units}
            />
          ))}
        </div>
      </div>
    </>
  );
}
function Product({
  // thumbnailUrl,
  category,
  productId,
  productTitle,
  totalPrice,
}: ProductFindResponse) {
  return (
    <>
      <Link to={"/product/" + productId} key={productId}>
        {/* <img src={thumbnailUrl} className="size-32 object-cover pb-1" /> */}
        <img
          src="img/extra-product-img.png"
          className="size-32 object-cover pb-1"
        />
        <div className="flex flex-row space-x-px pb-0.5">
          <div className="flex h-4 w-12 items-center justify-center rounded-full border border-blue1 ">
            {/* <p className="font-Gothic text-xxs">{category.mainCategory}</p> */}
            <p className="font-Gothic text-xxs">{category.mainCategory}</p>
          </div>
        </div>
        <p className="truncate pb-0.5 font-Gothic text-xs">{productTitle}</p>
        <p className="font-GothicBold text-xs">{totalPrice}원</p>
      </Link>
    </>
  );
}

export default InfoSale;

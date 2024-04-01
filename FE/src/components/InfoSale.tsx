// import Products from "@/components/ProductTypeR";
import { useAuthStore } from "@/stores/auth";
import { ProductFindResponse } from "@/types/Product";
import { useLocalAxios } from "@/utils/axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { MemberResponse } from "@/types/Member";
import Products from "./ProductTypeR";
import { Button } from "@/components/ui/button";

function InfoSale() {
  const localAxios = useLocalAxios();
  const auth = useAuthStore();

  const [products, setProduct] = useState<ProductFindResponse[]>([]);
  useEffect(() => {
    ProductsSetting();
  }, []);

  const ProductsSetting = async () => {
    const result = await localAxios.get<ProductFindResponse[]>(`/product`, {
      params: {
        page: 0,
        size: 5,
        memberId: auth.memberId,
      },
    });
    const json = result.data;
    console.log(json);
    setProduct(json);
  };

  const navigate = useNavigate();
  const Logout = async () => {
    await localAxios.delete("/member/logout");
    auth.clearAuth();
    navigate("/");
  };

  const categoryList: string[] = ["전체", "판매중", "거래중", "판매완료"];
  return (
    <>
      <div className="flex w-full flex-col items-center font-Gothic">
        <div className="m-10 w-3/4 text-sm">
          <div className="flex items-center justify-center">
            <div className="flex justify-between space-x-6">
              <div className="flex items-center">
                <img
                  src={auth.profileImage}
                  className="size-20 rounded-full object-cover"
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
                <p>오늘도 즐거운 쇼핑되세요 :D</p>
                <div className="flex justify-center">
                  <Button variant="logout" onClick={Logout}>
                    로그아웃
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <Tabs defaultValue="전체" className="m-0 w-full p-0 font-Gothic">
          <TabsList className="mb-2 grid w-full grid-cols-4 border-b border-b-gray1 p-0">
            {categoryList.map((category, index) => (
              <TabsTrigger
                key={index}
                value={category}
                className="h-full
          data-[state=active]:bg-black data-[state=active]:text-white1 "
              >
                {category}
              </TabsTrigger>
            ))}
            {/* </div> */}
            {/* </Tabs> */}
          </TabsList>
          <TabsContent value="전체">
            {/* 여기가 왜 안뜨지 */}
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
          </TabsContent>
        </Tabs>
      </div>
    </>
  );
}
function Product({
  category,
  productId,
  productTitle,
  totalPrice,
}: ProductFindResponse) {
  return (
    <>
      <Link to={"/product/" + productId} key={productId}>
        <img
          src="img/extra-product-img.png"
          className="size-32 object-cover pb-1"
        />
        <div className="flex flex-row space-x-px pb-0.5">
          <div className="flex h-4 w-12 items-center justify-center rounded-full border border-blue1 ">
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

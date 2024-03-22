import { useEffect, useState } from "react";
import { products } from "@/assets/dummydata/products.json";

interface productInterface {
  thumbnailUrl: string;
  category: string[];
  productId: string;
  productTitle: string;
  totalPrice: number;
}

function Product({
  thumbnailUrl,
  category,
  productId,
  productTitle,
  totalPrice,
}: productInterface) {
  return (
    <>
      <div>
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
      </div>
    </>
  );
}

function Products() {
  const [product, setProduct] = useState<productInterface[]>([]);
  //   const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async () => {
      const json: productInterface[] = products;
      setProduct(json);
      //   setLoading(false);
    })();
  }, []);

  return (
    <>
      <div className="flex flex-wrap px-7 ">
        <div className="grid grid-cols-2 gap-x-10 gap-y-7">
          {product.map((item: productInterface, index) => (
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
      </div>
    </>
  );
}

export default Products;

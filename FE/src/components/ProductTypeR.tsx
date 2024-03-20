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
  productTitle,
  totalPrice,
}: productInterface) {
  return (
    <>
      <div>
        <img src={thumbnailUrl} className="object-cover size-32 pb-1" />
        <div className="flex flex-row space-x-px pb-0.5">
          {category.map((temp) => (
            <div className="flex items-center justify-center w-12 h-4 rounded-full border border-blue1 ">
              <p className="font-Gothic text-xxs">{temp}</p>
            </div>
          ))}
        </div>
        <p className="font-Gothic text-xs pb-0.5 truncate">{productTitle}</p>
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
        <div className="grid gap-x-10 gap-y-7 grid-cols-2">
          {product.map((temp: productInterface) => (
            <Product
              key={temp.productId}
              thumbnailUrl={temp.thumbnailUrl}
              category={temp.category}
              productId={temp.productId}
              productTitle={temp.productTitle}
              totalPrice={temp.totalPrice}
            />
          ))}
        </div>
      </div>
    </>
  );
}

export default Products;

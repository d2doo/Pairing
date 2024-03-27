//https://github.com/shadcn-ui/ui/issues/250

import { ChangeEvent, useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "./ui/input";
import { useForm } from "react-hook-form";
import { Label } from "./ui/label";
import { useRef } from "react";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import * as zod from "zod";

const RegisterCircleInputClientSchema = zod.object({
  circle_image: zod.string(), // Assuming the circle image is a URL or file path
  // Add more fields as needed
});

type RegisterCircleInputClient = zod.infer<
  typeof RegisterCircleInputClientSchema
>;

// Define schema for registerCircleSchemaClient
const registerCircleSchemaClient = RegisterCircleInputClientSchema;

// export type { RegisterCircleInputClient, registerCircleSchemaClient };

function getImageData(event: ChangeEvent<HTMLInputElement>) {
  // FileList is immutable, so we need to create a new one
  // Add newly uploaded images
  const dataTransfer = new DataTransfer();

  Array.from(event.target.files!).forEach((image) =>
    dataTransfer.items.add(image),
  );

  const files = dataTransfer.files;
  const displayUrl = URL.createObjectURL(event.target.files![0]);
  return { files, displayUrl };
}

function AddImage() {
  const [previewList, setPreviewList] = useState<string[]>([]);
  const input = useRef<HTMLInputElement>(null);
  const form = useForm<RegisterCircleInputClient>({
    mode: "onSubmit",
    resolver: zodResolver(registerCircleSchemaClient),
  });

  function submitCircleRegistration(value: RegisterCircleInputClient) {
    console.log({ value });
  }

  function checkCount() {
    previewList.length >= 5 ? console.log("이제 그만") : console.log("????");
  }

  function deleteImage(indexToDelete: number) {
    const updatedPreviewList = previewList.filter(
      (_, index) => index !== indexToDelete,
    );
    // 업데이트된 배열로 previewList 상태를 갱신합니다.
    setPreviewList(updatedPreviewList);
  }

  return (
    <>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(submitCircleRegistration)}>
          <div className="flex space-x-2">
            <div
              className="flex size-16 flex-col items-center justify-center space-y-2 rounded border border-gray1"
              onClick={() => {
                previewList.length >= 5
                  ? console.log("이제 그만")
                  : input.current?.click();
                console.log(previewList);
              }}
            >
              <img src="/img/camera-img.png" className="w-6 " />
              <Label>{previewList.length}/5</Label>
            </div>
            {previewList.map((preview, index) => (
              <div className="relative ">
                <div
                  key={index}
                  className="flex size-16 flex-col items-center justify-center rounded border border-gray1"
                >
                  <img
                    src={preview}
                    className="h-full w-full object-scale-down"
                  />
                  <div onClick={() => deleteImage(index)}>
                    <img
                      src="/img/x-btn.png"
                      className="absolute -right-1 -top-1 size-4"
                    />
                  </div>
                </div>
              </div>
            ))}
          </div>
          <FormField
            control={form.control}
            name="circle_image"
            render={({ field: { onChange, value, ...rest } }) => (
              <>
                <FormItem>
                  <FormControl>
                    <Input
                      className="hidden"
                      type="file"
                      {...rest}
                      ref={input}
                      onClick={checkCount}
                      onChange={(event) => {
                        const { files, displayUrl } = getImageData(event);
                        setPreviewList([...previewList, displayUrl]);
                        onChange(files);
                      }}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              </>
            )}
          />
        </form>
      </Form>
    </>
  );
}
export default AddImage;

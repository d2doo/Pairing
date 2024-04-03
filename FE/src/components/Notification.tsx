import { useEffect, useState } from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { NotificationResponse } from "@/types/Notification";
import NotificationDetail from "./NotificationDetail";
interface NotificationProps {
  openhandler: (notificationId: bigint) => void;
  closehandler: (notificationId: bigint) => void;
  notification: NotificationResponse;
}
function Notification(props: NotificationProps) {
  return (
    <>
      <div className="flex w-full flex-wrap">
        <AlertDialog>
          <AlertDialogTrigger asChild>
            <Button variant="outline" className="my-1 space-y-1 text-sm">
              자세히 보기
            </Button>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>
                {props.notification?.notificationType == "confirm"
                  ? "합의 제안"
                  : "일반 알림"}
              </AlertDialogTitle>
              <AlertDialogDescription>
                <div className="py-1">
                  {/* <NotificationDetail key={props.notification?.notificationId} notification={props.notification} /> */}
                  <Card className="rounded-md border border-gray-200 bg-white font-[Gothic] shadow-md">
                    <CardHeader className="rounded-t-md bg-blue1 text-white">
                      <CardTitle className="flex items-center">
                        {/* 포인트 색상 설정 */}
                        <div className="mr-2 flex h-6 w-6 items-center justify-center rounded-full bg-yellow-500">
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            className="h-4 w-4"
                            viewBox="0 0 20 20"
                            fill="currentColor"
                          >
                            <path
                              fillRule="evenodd"
                              d="M10 2a1 1 0 011 1v7h4a1 1 0 010 2h-5v1a1 1 0 01-2 0V12H5a1 1 0 010-2h4V3a1 1 0 011-1z"
                              clipRule="evenodd"
                            />
                            <path
                              fillRule="evenodd"
                              d="M3 8a1 1 0 011-1h8V5a1 1 0 012 0v2h3a1 1 0 110 2h-3v2a1 1 0 01-2 0V9H4a1 1 0 01-1-1z"
                              clipRule="evenodd"
                            />
                          </svg>
                        </div>
                        {/* 내용 */}
                        <div>{props.notification?.content}</div>
                      </CardTitle>
                    </CardHeader>
                    <CardContent>
                      <div className="flex flex-row justify-around">
                        {props.notification.units &&
                          props.notification.units.map((unit, index) => (
                            <div
                              key={index}
                              className="mt-6 rounded-md border border-gray-200"
                            >
                              <img
                                src={
                                  unit.unitImages[0]
                                    ? unit.unitImages[0]
                                    : "../assets/images/case.png"
                                }
                                alt="Unit Image"
                                className="h-auto w-full rounded-t-md object-cover"
                              />
                              <div className="p-4">
                                <div>주인: {unit.nickname}</div>
                                <div>연식: {unit.age}</div>
                                <div>신뢰도: {unit.score}</div>
                              </div>
                            </div>
                          ))}
                      </div>
                    </CardContent>
                  </Card>
                </div>
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter className="flex flex-row justify-end">
              <AlertDialogCancel className="w-12">
                <Button
                  onClick={() =>
                    props.closehandler(props.notification.notificationId)
                  }
                  className="h-full bg-white"
                >
                  {props.notification?.notificationType == "confirm"
                    ? "X"
                    : "닫기"}
                </Button>
              </AlertDialogCancel>
              <AlertDialogAction className="w-12">
                <Button
                  onClick={() =>
                    props.openhandler(props.notification.notificationId)
                  }
                >
                  {props.notification?.notificationType == "confirm"
                    ? "O"
                    : "읽기"}
                </Button>
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </div>
    </>
  );
}

export default Notification;

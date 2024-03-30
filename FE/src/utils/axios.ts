import {useState} from "react";
import {useAuthStore} from "@/stores/auth.ts";
import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse } from 'axios';

export const useLocalAxios = (isAuthenticated: boolean = true) => {

    const authStore = useAuthStore();
    const [isRefreshing, setIsRefreshing] = useState(false);
    const [refreshSubscribers, setRefreshSubscribers] = useState<((newAccessToken: string) => void)[]>([]);

    const addRefreshSubscriber = (callback: (newAccessToken: string) => void) => {
        setRefreshSubscribers((prev) => [...prev, callback]);
    };

    const onRefreshed = (newAccessToken: string) => {
        refreshSubscribers.forEach((callback) => callback(newAccessToken));
        setRefreshSubscribers([]);
        setIsRefreshing(false);
    };

    const refreshAccessToken = async () => {
        try {
            const response = await axios.post<{ accessToken: string }>(`${import.meta.env.VITE_API_BASE_URL}/refresh`);
            const newAccessToken = response.data.accessToken;
            authStore.setAccessToken(newAccessToken);
            onRefreshed(newAccessToken);
        } catch (error) {
            authStore.clearAuth();
        }
    };

    const axiosInstance: AxiosInstance = axios.create({
        baseURL: import.meta.env.VITE_API_BASE_URL,
        headers: {
            'Content-Type': 'application/json',
        },
        withCredentials: true
    });

    axiosInstance.interceptors.request.use(
        (config: InternalAxiosRequestConfig) => {
            if (isAuthenticated && authStore.accessToken) {
                config.headers!.Authorization = authStore.accessToken;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (response: AxiosResponse) => response,
        async (error) => {
            const originalRequest = error.config;
            if (error.response?.status === 401 && !originalRequest?._retry) {
                if (isRefreshing) {
                    try {
                        const newAccessToken = await new Promise<string>((resolve) => {
                            addRefreshSubscriber((newAccessToken) => {
                                resolve(newAccessToken);
                            });
                        });
                        originalRequest.headers!.Authorization = newAccessToken;
                        return axiosInstance(originalRequest);
                    } catch (error) {
                        return Promise.reject(error);
                    }
                }

                originalRequest._retry = true;
                setIsRefreshing(true);
                try {
                    await refreshAccessToken();
                } catch (error) {
                    return Promise.reject(error);
                }

                originalRequest.headers!.Authorization = authStore.accessToken;
                return axiosInstance(originalRequest);
            }

            return Promise.reject(error);
        }
    );

    return axiosInstance;
};
interface MemberResponse extends MemberSummaryResponse {
    email: string;
    provider: string;
    address: string;
    phoneNumber: string;
}

interface MemberLoginResponse {
    accessToken: string;
    memberInfo: MemberSummaryResponse;
}

interface MemberSummaryResponse {
    memberId: string;
    nickname: string;
    profileImage: string;
    score: number;
}

export type { MemberResponse, MemberLoginResponse, MemberSummaryResponse };
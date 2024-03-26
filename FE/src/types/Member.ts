interface MemberResponse extends MemberSummaryResponse {
    email: string;
    provider: string;
    address: string;
    phoneNumber: string;
}

interface MemberSummaryResponse {
    memberId: string;
    nickname: string;
    profileImage: string;
    score: number;
}

export type { MemberResponse, MemberSummaryResponse };

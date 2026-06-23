export interface Stats{
    title: string;
    teamScore: number;
    matches: number;
    wins: number;
    losses: number;
    draws: number;
}
export interface Table{
    data: Stats[];
}
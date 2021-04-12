import { Tag } from "./tag";
import { User } from "./user";

export class Post {
    id: number;
    title: string;
    content: string;
    create_at: Date;
    comments: Comment;
    user: User;
    tag: Tag[];
}

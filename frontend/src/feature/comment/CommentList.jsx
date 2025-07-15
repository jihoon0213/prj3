import { useEffect, useState } from "react";
import axios from "axios";
import { Spinner } from "react-bootstrap";

function CommentItem({ comment }) {
  return (
    <div className="border m-3">
      <div className="d-flex justify-content-between m-3">
        <div>{comment.authorNickName}</div>
        <div>{comment.timesAgo}</div>
      </div>
      <div>{comment.comment}</div>
    </div>
  );
}

export function CommentList({ boardId }) {
  const [commentList, setCommentList] = useState(null);

  useEffect(() => {
    axios
      .get(`/api/comment/board/${boardId}`)
      .then((res) => {
        setCommentList(res.data);
      })
      .catch((err) => {})
      .finally(() => {});
  }, []);

  if (commentList === null) {
    return <Spinner />;
  }

  return (
    <div>
      {commentList.map((comment) => (
        <CommentItem comment={comment} key={comment.id} />
      ))}
    </div>
  );
}

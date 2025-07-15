import { CommentAdd } from "./CommentAdd.jsx";
import { CommentList } from "./CommentList.jsx";
import { useEffect, useState } from "react";
import axios from "axios";
import { Spinner } from "react-bootstrap";

export function CommentContainer({ boardId }) {
  const [isProcessing, setIsProcessing] = useState(false);
  const [commentList, setCommentList] = useState(null);

  useEffect(() => {
    if (!isProcessing) {
      axios
        .get(`/api/comment/board/${boardId}`)
        .then((res) => {
          setCommentList(res.data);
        })
        .catch((err) => {})
        .finally(() => {});
    }
  }, [isProcessing]);

  if (commentList === null) {
    return <Spinner />;
  }

  return (
    <div>
      <h4 className="mb-3">댓글 ({commentList.length})</h4>

      <CommentAdd
        boardId={boardId}
        isProcessing={isProcessing}
        setIsProcessing={setIsProcessing}
      />
      <CommentList
        commentList={commentList}
        isProcessing={isProcessing}
        setIsProcessing={setIsProcessing}
      />
    </div>
  );
}

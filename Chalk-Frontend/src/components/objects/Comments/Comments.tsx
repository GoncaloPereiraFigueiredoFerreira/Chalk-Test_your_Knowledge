export function Comments(comments: string[]) {
  console.log(comments["comments"]);
  return (
    <div className=" flex flex-col border-t-2 mb-4">
      {comments["comments"].map((comment: string) => (
        <div className="m-2 text-black">{"> " + comment}</div>
      ))}
      <form className="">
        <div className="py-2 px-4 bg-white rounded-lg rounded-t-lg border border-gray-200 dark:bg-gray-800 dark:border-gray-700">
          <label className="sr-only"></label>
          <textarea
            id="comment"
            className="px-0 w-full text-sm text-gray-900 border-0 focus:ring-0 focus:outline-none dark:text-white dark:placeholder-gray-400 dark:bg-gray-800"
            placeholder="Write a comment..."
            required
          ></textarea>
        </div>
        <button
          type="submit"
          className="bg-gray-700 mt-2 float-right inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white rounded-lg focus:ring-4 focus:ring-primary-200 dark:focus:ring-primary-900 hover:bg-gray-700"
        >
          Post comment
        </button>
      </form>
    </div>
  );
}

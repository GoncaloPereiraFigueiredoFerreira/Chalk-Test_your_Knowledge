import { Carousel } from "flowbite";

export function Slide() {
  const carouselElement = document.getElementById("carousel-example");

  const items = [
    {
      position: 0,
      el: document.getElementById("carousel-item-1"),
    },
    {
      position: 1,
      el: document.getElementById("carousel-item-2"),
    },
    {
      position: 2,
      el: document.getElementById("carousel-item-3"),
    },
    {
      position: 3,
      el: document.getElementById("carousel-item-4"),
    },
  ];

  // options with default values
  const options = {
    defaultPosition: 1,
    interval: 3000,

    indicators: {
      activeClasses: "bg-white dark:bg-gray-800",
      inactiveClasses:
        "bg-white/50 dark:bg-gray-800/50 hover:bg-white dark:hover:bg-gray-800",
      items: [
        {
          position: 0,
          el: document.getElementById("carousel-indicator-1"),
        },
        {
          position: 1,
          el: document.getElementById("carousel-indicator-2"),
        },
        {
          position: 2,
          el: document.getElementById("carousel-indicator-3"),
        },
        {
          position: 3,
          el: document.getElementById("carousel-indicator-4"),
        },
      ],
    },

    // callback functions
    onNext: () => {
      console.log("next slider item is shown");
    },
    onPrev: () => {
      console.log("previous slider item is shown");
    },
    onChange: () => {
      console.log("new slider item has been shown");
    },
  };

  // instance options object
  const instanceOptions = {
    id: "carousel-example",
    override: true,
  };
  const carousel = new Carousel(
    carouselElement,
    items,
    options,
    instanceOptions
  );

  const $prevButton = document.getElementById("data-carousel-prev");
  const $nextButton = document.getElementById("data-carousel-next");

  $prevButton.addEventListener("click", () => {
    carousel.prev();
  });

  $nextButton.addEventListener("click", () => {
    carousel.next();
  });

  return (
    <>
      <div id="carousel-example" className="relative w-full">
        {/**<!-- Carousel wrapper -->*/}
        <div className="relative h-56 overflow-hidden rounded-lg sm:h-64 xl:h-80 2xl:h-96">
          <div id="carousel-item-1" className="hidden duration-700 ease-in-out">
            <img
              src="chico.jpg"
              className="absolute left-1/2 top-1/2 block w-full -translate-x-1/2 -translate-y-1/2"
              alt="..."
            />
          </div>
          <div id="carousel-item-2" className="hidden duration-700 ease-in-out">
            <img
              src="bronze.jpg"
              className="absolute left-1/2 top-1/2 block w-full -translate-x-1/2 -translate-y-1/2"
              alt="..."
            />
          </div>
          <div id="carousel-item-3" className="hidden duration-700 ease-in-out">
            <img
              src="/docs/images/carousel/carousel-3.svg"
              className="absolute left-1/2 top-1/2 block w-full -translate-x-1/2 -translate-y-1/2"
              alt="..."
            />
          </div>
          <div id="carousel-item-4" className="hidden duration-700 ease-in-out">
            <img
              src="/docs/images/carousel/carousel-4.svg"
              className="absolute left-1/2 top-1/2 block w-full -translate-x-1/2 -translate-y-1/2"
              alt="..."
            />
          </div>
        </div>
        {/**<!-- Slider indicators -->*/}
        <div className="absolute bottom-5 left-1/2 z-30 flex -translate-x-1/2 space-x-3 rtl:space-x-reverse">
          <button
            id="carousel-indicator-1"
            type="button"
            className="h-3 w-3 rounded-full"
            aria-current="true"
            aria-label="Slide 1"
            data-carousel-slide-to="0"
          ></button>
          <button
            id="carousel-indicator-2"
            type="button"
            className="h-3 w-3 rounded-full"
            aria-current="false"
            aria-label="Slide 2"
            data-carousel-slide-to="1"
          ></button>
          <button
            id="carousel-indicator-3"
            type="button"
            className="h-3 w-3 rounded-full"
            aria-current="false"
            aria-label="Slide 3"
            data-carousel-slide-to="2"
          ></button>
          <button
            id="carousel-indicator-4"
            type="button"
            className="h-3 w-3 rounded-full"
            aria-current="false"
            aria-label="Slide 4"
            data-carousel-slide-to="3"
          ></button>
        </div>

        {/**<!-- Slider controls -->*/}
        <button
          id="data-carousel-prev"
          type="button"
          className="group absolute left-0 top-0 z-30 flex h-full cursor-pointer items-center justify-center px-4 focus:outline-none"
        >
          <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-white/30 group-hover:bg-white/50 group-focus:outline-none group-focus:ring-4 group-focus:ring-white dark:bg-gray-800/30 dark:group-hover:bg-gray-800/60 dark:group-focus:ring-gray-800/70">
            <svg
              className="h-4 w-4 text-white dark:text-gray-800"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 1 1 5l4 4"
              />
            </svg>
            <span className="hidden">Previous</span>
          </span>
        </button>
        <button
          id="data-carousel-next"
          type="button"
          className="group absolute right-0 top-0 z-30 flex h-full cursor-pointer items-center justify-center px-4 focus:outline-none"
        >
          <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-white/30 group-hover:bg-white/50 group-focus:outline-none group-focus:ring-4 group-focus:ring-white dark:bg-gray-800/30 dark:group-hover:bg-gray-800/60 dark:group-focus:ring-gray-800/70">
            <svg
              className="h-4 w-4 text-white dark:text-gray-800"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 9 4-4-4-4"
              />
            </svg>
            <span className="hidden">Next</span>
          </span>
        </button>
      </div>
    </>
  );
}

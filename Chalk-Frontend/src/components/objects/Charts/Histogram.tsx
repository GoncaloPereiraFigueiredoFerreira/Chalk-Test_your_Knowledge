// src/components/Histogram.tsx
import React, { useEffect, useRef } from "react";
import Chart from "chart.js/auto";

interface HistogramProps {
  data: number[];
}

const Histogram: React.FC<HistogramProps> = ({ data }) => {
  const chartRef = useRef<HTMLCanvasElement | null>(null);
  const chartInstance = useRef<Chart | null>(null);
  const grade_distribution = ["F", "D", "C", "B", "A"];
  useEffect(() => {
    const ctx = chartRef.current?.getContext("2d");

    if (ctx) {
      if (chartInstance.current) {
        // If a Chart instance already exists, destroy it before creating a new one
        chartInstance.current.destroy();
      }

      chartInstance.current = new Chart(ctx, {
        type: "bar",
        data: {
          labels: grade_distribution.map((grade) => ` ${grade}`),
          datasets: [
            {
              label: "Grade Distribution",
              data: data,
              backgroundColor: "rgba(75, 192, 192, 0.7)",
              borderColor: "rgba(75, 192, 192, 1)",
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: true, // Enable responsiveness
          maintainAspectRatio: false, // Allow the chart to exceed its container size
          scales: {
            x: {
              beginAtZero: true,
            },
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    }

    return () => {
      // Cleanup: destroy the Chart instance when the component is unmounted
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [data]);

  return (
    <div className="max-w-screen-xl mx-auto my-8 dark:bg-slate-400">
      <canvas ref={chartRef} width={800} height={400}></canvas>{" "}
    </div>
  );
};

export default Histogram;

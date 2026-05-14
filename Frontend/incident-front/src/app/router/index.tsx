import { createBrowserRouter, Navigate } from "react-router-dom";
import { UIPage } from "../../pages/UiPage";
import { LoginPage } from "@/pages/LoginPage";
import { MobileIncidentReportPage } from "@/pages/MobileIncidentReportPage";
import { TechnicianQueuePage } from "@/pages/TechnicianQueuePage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/auth" replace />,
  },
  {
    path: "/ui",
    element: <UIPage />,
  },
  {
    path: "/auth",
    element: <LoginPage />,
  },
  {
    path: "/check",
    element: <MobileIncidentReportPage />,
  },
  {
    path: "/tec-queue",
    element: <TechnicianQueuePage />,
  },
]);

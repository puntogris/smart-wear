import { Colors } from "@/constants/Colors";
import { Stack } from "expo-router";

export default function Layout() {
  return (
    <Stack
      screenOptions={{
        presentation: "transparentModal",
        headerStyle: {
          backgroundColor: Colors.sky,
        },
        headerTintColor: "#fff",
        statusBarBackgroundColor: Colors.sky,
        contentStyle: {
          backgroundColor: Colors.sky,
        },
      }}
    >
      <Stack.Screen name="index" />
      <Stack.Screen name="settings" />
    </Stack>
  );
}

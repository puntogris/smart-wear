import { Colors } from "@/constants/Colors";
import { Stack } from "expo-router";
import { SafeAreaView, Text, StyleSheet } from "react-native";

export default function Settings() {
  return (
    <SafeAreaView style={styles.container}>
      <Stack.Screen
        options={{
          title: "Settings",
          headerStyle: {
            backgroundColor: Colors.sky,
          },
          headerShadowVisible: false,
          headerShown: true,
        }}
      />
      <Text>Settings</Text>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: Colors.sky,
  },
});

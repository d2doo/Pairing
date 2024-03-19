/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ["class"],
  content: [
    "./pages/**/*.{ts,tsx}",
    "./components/**/*.{ts,tsx}",
    "./app/**/*.{ts,tsx}",
    "./src/**/*.{ts,tsx}",
  ],
  prefix: "",
  theme: {
    extend: {
      boxShadow: {
        "3xl": "0 4px 4px 0 rgba(0, 0, 0, 0.25)",
        "4xl": "0 1px 2px rgba(0, 0, 0, 0.25)",
      },
      textShadow: {
        default: "0px 4px 4px rgba(0, 0, 0, 0.3)",
        md: "0px 4px 4px rgba(0, 0, 0, 0.25)",
      },
      container: {
        center: true,
        padding: "2rem",
        screens: {
          "2xl": "1400px",
        },
      },
      fontFamily: {
        Gothic: ["Gothic"],
        GothicBold: ["GothicBold"],
        GothicMedium: ["GothicMedium"],
        GothicLight: ["GothicLight"],
        Chab: ["Chab"],
      },
      fontSize: {
        logo: ["3rem", "3.2rem"],
      },
      backgroundImage: {
        "text-gradient":
          "linear-gradient(180deg, rgba(182, 208, 242, 0.75) 0%, rgba(160, 172, 242, 0.75) 100%)",
        "bg-gradient":
          "linear-gradient(0deg, rgba(255, 255, 255, 1) 0%, rgba(206, 220, 248, 1) 66%, rgba(162, 189, 242, 1) 100%)",
      },
      textFillColor: {
        transparent: "transparent",
      },
      colors: {
        blue1: "#A2BDF2",
        blue2: "#B6D0F2",
        blue3: "#C4D5F5",
        gray1: "#C9C9C9",
        black1: "#2B3136",
        white1: "#FFFFFF",
        white2: "#F2F2F2",
        purple1: "#A0ACF2",
        border: "hsl(var(--border))",
        input: "hsl(var(--input))",
        ring: "hsl(var(--ring))",
        background: "hsl(var(--background))",
        foreground: "hsl(var(--foreground))",

        primary: {
          DEFAULT: "hsl(var(--primary))",
          foreground: "hsl(var(--primary-foreground))",
        },
        secondary: {
          DEFAULT: "hsl(var(--secondary))",
          foreground: "hsl(var(--secondary-foreground))",
        },
        destructive: {
          DEFAULT: "hsl(var(--destructive))",
          foreground: "hsl(var(--destructive-foreground))",
        },
        muted: {
          DEFAULT: "hsl(var(--muted))",
          foreground: "hsl(var(--muted-foreground))",
        },
        accent: {
          DEFAULT: "hsl(var(--accent))",
          foreground: "hsl(var(--accent-foreground))",
        },
        popover: {
          DEFAULT: "hsl(var(--popover))",
          foreground: "hsl(var(--popover-foreground))",
        },
        card: {
          DEFAULT: "hsl(var(--card))",
          foreground: "hsl(var(--card-foreground))",
        },
      },
      borderRadius: {
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
      },
    },
  },
  plugins: [
    require("tailwindcss-animate"),
    require("@tailwindcss/forms"),
    function ({ addUtilities, theme }) {
      const newUtilities = {};
      Object.entries(theme("textShadow")).forEach(([key, value]) => {
        const className = `.text-shadow-${key}`;
        newUtilities[className] = {
          textShadow: value,
        };
      });
      addUtilities(newUtilities, ["responsive", "hover"]);
    },
  ],
};

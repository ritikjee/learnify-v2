"use client";

import { useAppSelector } from "@/redux/store";
import React from "react";
import { SearchGroups } from "./searched-groups";
import GroupList from "./group-list";
import ExploreSlider from "./explore-slider";

type Props = {
  layout: "SLIDER" | "LIST";
  category?: string;
};

function ExploreContent({ layout, category }: Props) {
  const { isSearching, data, status, debounce } = useAppSelector(
    (state) => state.searchReducer
  );

  return (
    <div className="flex flex-col">
      {isSearching || debounce ? (
        <SearchGroups
          searching={isSearching as boolean}
          data={data}
          query={debounce}
        />
      ) : (
        status !== 200 &&
        (layout === "SLIDER" ? (
          <>
            <ExploreSlider
              label="Fitness"
              text="Join top performing groups on grouple."
              query="fitness"
            />
            <ExploreSlider
              label="Lifestyle"
              text="Join top performing groups on grouple."
              query="lifestyle"
            />
            <ExploreSlider
              label="Music"
              text="Join top performing groups on grouple."
              query="music"
            />
          </>
        ) : (
          <GroupList category={category as string} />
        ))
      )}
    </div>
  );
}

export default ExploreContent;

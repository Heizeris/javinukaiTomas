import React, { useState, useEffect } from "react";
import axios from "axios";

function ContestPreview() {
  const [contests, setContests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("recent");

  useEffect(() => {
    const fetchContests = async () => {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_BACKEND}/api/v1/contests`
        );
        setContests(response.data.content);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching contests:", error);
      }
    };

    fetchContests();
  }, []);

  const filteredContests = contests.filter((contest) =>
    contest.contestName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const sortedContests = filteredContests.sort((a, b) => {
    if (sortBy === "recent") {
      return new Date(b.createdAt) - new Date(a.createdAt);
    } else if (sortBy === "totalCategories") {
      return b.categories.length - a.categories.length;
    }
    return 0;
  });

  return (
    <div className="p-4">
      <h2 className="text-2xl font-semibold mb-4">Contest Previews</h2>
      <div className="flex justify-between mb-4">
        <div className="mb-4 flex">
          <input
            type="text"
            placeholder="Search by name..."
            className="border border-gray-300 rounded-md px-3 py-1 mr-2"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <select
            value={sortBy}
            onChange={(e) => setSortBy(e.target.value)}
            className="border border-gray-300 rounded-md px-3 py-1"
          >
            <option value="recent">Recently Added</option>
            <option value="totalCategories">Most Categories</option>
          </select>
        </div>
      </div>
      {loading ? (
        <p className="text-gray-600">Loading...</p>
      ) : (
        <ul>
          {sortedContests.map((contest) => (
            <li key={contest.id} className="border rounded-md p-4 mb-4">
              <h3 className="text-lg font-semibold mb-2">
                {contest.contestName}
              </h3>
              <p className="text-gray-600 mb-2">{contest.description}</p>
              <p className="text-gray-700 mb-2">
                Total Submissions: {contest.totalSubmissions}
              </p>
              <p className="text-gray-700 mb-2">
                Start Date: {new Date(contest.startDate).toLocaleDateString()}
              </p>
              <p className="text-gray-700 mb-2">
                End Date: {new Date(contest.endDate).toLocaleDateString()}
              </p>
              <div className="flex flex-wrap">
                {contest.categories.map((category) => (
                  <div key={category.id} className="border rounded-md p-2 mr-2 mb-2">
                    <p className="text-sm font-semibold">{category.categoryName} - {category.type}</p>
                    <p className="text-xs">{category.description}</p>
                    <p className="text-xs">Total Submissions: {category.totalSubmissions}</p>
                  </div>
                ))}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default ContestPreview;

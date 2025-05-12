import { useState } from "react";

const SearchBox = () => {
    const [searchQuery, setSearchQuery] = useState('');
    return ( 
        <div className="flex-grow mx-4 max-w-md">
            <input 
            type="text" 
            className="w-full px-4 py-2 border border-gray-300 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            value={searchQuery} 
            onChange={e => setSearchQuery(e.target.value)}
            placeholder="Search..." />
        </div>
     );
}
 
export default SearchBox;